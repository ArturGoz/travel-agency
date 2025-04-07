package com.epam.finaltask.controller;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherRequest;
import com.epam.finaltask.exception.EntityNotFoundException;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.model.*;
import com.epam.finaltask.service.UserService;
import com.epam.finaltask.service.VoucherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class VoucherControllerTest {
    @MockBean
    private VoucherService voucherService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "MANAGER_UPDATE")
    void findAll_Successfully() throws Exception {
        List<VoucherDTO> voucherDTOList = new ArrayList<>();
        when(voucherService.findAll()).thenReturn(voucherDTOList);
        MvcResult result = mockMvc.perform(get("/manager/vouchers/list"))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode resultsNode = rootNode.path("results");

        List<VoucherDTO> responseVoucherDTOList = objectMapper.convertValue(resultsNode, new TypeReference<List<VoucherDTO>>() {
        });

        assertEquals(voucherDTOList, responseVoucherDTOList);
    }


    @Test
    @WithMockUser(authorities = "ADMIN_CREATE")
    void createVoucher_ValidData_SuccessfullyCreated() throws Exception {
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setTitle("SummerSale2024");
        voucherDTO.setDescription("Summer Sale Voucher Description");
        voucherDTO.setPrice(299.99);
        voucherDTO.setTourType(TourType.ADVENTURE.name());
        voucherDTO.setTransferType(TransferType.PLANE.name());
        voucherDTO.setHotelType(HotelType.FIVE_STARS.name());
        voucherDTO.setStatus(VoucherStatus.PAID.name());
        voucherDTO.setArrivalDate(LocalDate.of(2024, 6, 15));
        voucherDTO.setEvictionDate(LocalDate.of(2024, 6, 20));
        voucherDTO.setId(UUID.randomUUID().toString());
        voucherDTO.setIsHot("false");

        String expectedStatusCode = "OK";
        String expectedMessage = "Voucher is successfully created";

        when(voucherService.create(any(VoucherDTO.class))).thenReturn(voucherDTO);

        mockMvc.perform(post("/admin/vouchers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(voucherDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(expectedStatusCode))
                .andExpect(jsonPath("$.statusMessage").value(expectedMessage));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_UPDATE")
    void updateVoucher_ValidData_SuccessfullyUpdated() throws Exception {
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setTitle("UpdatedTitle");
        voucherDTO.setDescription("Updated description");
        voucherDTO.setPrice(499.99);
        voucherDTO.setTourType(TourType.SAFARI.name());
        voucherDTO.setTransferType(TransferType.JEEPS.name());
        voucherDTO.setHotelType(HotelType.THREE_STARS.name());
        voucherDTO.setStatus(VoucherStatus.PAID.name());
        voucherDTO.setArrivalDate(LocalDate.of(2024, 7, 15));
        voucherDTO.setEvictionDate(LocalDate.of(2024, 7, 20));
        voucherDTO.setId(UUID.randomUUID().toString());
        voucherDTO.setIsHot("true");

        String voucherId = String.valueOf(UUID.randomUUID());
        String expectedStatusCode = "OK";
        String expectedMessage = "Voucher is successfully updated";

        when(voucherService.update(eq(voucherId), any(VoucherDTO.class))).thenReturn(voucherDTO);

        mockMvc.perform(patch("/admin/vouchers/change/" + voucherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(voucherDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(expectedStatusCode))
                .andExpect(jsonPath("$.statusMessage").value(expectedMessage));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_DELETE")
    void deleteVoucherById_VoucherExists_SuccessfullyDeleted() throws Exception {
        String voucherId = String.valueOf(UUID.randomUUID());
        String expectedStatusCode = "OK";
        String expectedMessage = String.format("Voucher with Id %s has been deleted", voucherId);

        doNothing().when(voucherService).delete(voucherId);

        mockMvc.perform(delete("/admin/vouchers/" + voucherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(expectedStatusCode))
                .andExpect(jsonPath("$.statusMessage").value(expectedMessage));

        verify(voucherService, times(1)).delete(voucherId);
    }

    @Test
    @WithMockUser(authorities = "ADMIN_DELETE")
    void deleteVoucherById_VoucherDoesNotExist_NotFound() throws Exception {
        String voucherId = String.valueOf(UUID.randomUUID());
        String expectedStatusCode = StatusCodes.ENTITY_NOT_FOUND.name();
        String expectedMessage = String.format("Voucher with Id %s not found", voucherId);

        doThrow(new EntityNotFoundException(expectedMessage, expectedStatusCode))
                .when(voucherService).delete(voucherId);

        mockMvc.perform(delete("/admin/vouchers/" + voucherId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(expectedStatusCode))
                .andExpect(jsonPath("$.statusMessage").value(expectedMessage));

        verify(voucherService, times(1)).delete(voucherId);
    }

    @Test
    @WithMockUser(authorities = "USER_UPDATE")
    void orderVoucher_ValidRequest_Success() throws Exception {
        String username = "user123";
        String voucherId = UUID.randomUUID().toString();
        VoucherRequest request = new VoucherRequest(voucherId, null);

        VoucherDTO orderedVoucher = new VoucherDTO();
        when(voucherService.order(voucherId, username)).thenReturn(orderedVoucher);

        mockMvc.perform(post("/user/vouchers/order")
                        .header("X-User-Name", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("voucher was successfully ordered"));
    }

    @Test
    @WithMockUser(authorities = "USER_UPDATE")
    void orderVoucher_InvalidVoucherId_NotFound() throws Exception {
        String username = "user123";
        String voucherId = "invalid";
        VoucherRequest request = new VoucherRequest(voucherId, null);
        when(voucherService.order(voucherId, username)).thenThrow(new EntityNotFoundException("Voucher not found", StatusCodes.ENTITY_NOT_FOUND.name()));

        mockMvc.perform(post("/user/vouchers/order")
                        .header("X-User-Name", username)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void getVouchers_WithPagination_Success() throws Exception {
        Page<Voucher> mockPage = new PageImpl<>(List.of(new Voucher()), PageRequest.of(0, 3), 1);
        when(voucherService.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/user/vouchers")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].totalItems").value(1));
    }

    @Test
    @WithMockUser(authorities = "ADMIN_READ")
    void getAllVouchersByStatus_ValidStatus_Success() throws Exception {
        String status = "PAID";
        List<VoucherDTO> vouchers = List.of(new VoucherDTO());
        when(voucherService.findAllByStatus(status)).thenReturn(vouchers);

        mockMvc.perform(get("/admin/vouchers/status/" + status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(StatusCodes.OK.name()));
    }

}
