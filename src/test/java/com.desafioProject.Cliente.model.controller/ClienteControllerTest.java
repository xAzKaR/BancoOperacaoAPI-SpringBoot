//package com.desafioProject.Cliente.model.controller;
//
//import com.desafioProject.Cliente.api.controller.ClienteController;
//import com.desafioProject.Cliente.api.dto.ClienteDto;
//import com.desafioProject.Cliente.service.ClienteService;
//import org.hamcrest.Matchers;
//import org.hamcrest.core.Is;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Transactional
//public class ClienteControllerTest {
//
//    private static final String ENDPOINT_URL = "/api/cliente;@InjectMocks";
//    private ClienteController clienteController;
//
//    @MockBean
//    private ClienteService clienteService;
//
//    @MockBean
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(clienteController)
//                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                //.addFilter(CustomFilter::doFilter)
//                .build();
//    }
//
////    @Test
////    public void findAllByPage() throws Exception {
////        Page<ClienteDto> page = new PageImpl<>(Collections.singletonList(ClienteBuilder.getDto()));
////
////        Mockito.when(clienteService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);
////
////        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));
////
////        Mockito.verify(clienteService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
////        Mockito.verifyNoMoreInteractions(clienteService);
////
////    }
//
//    @Test
//    public void getById() throws Exception {
//        Mockito.when(clienteService.findById(ArgumentMatchers.anyLong())).thenReturn(ClienteBuilder.getDto());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
//        Mockito.verify(clienteService, Mockito.times(1)).findById(1L);
//        Mockito.verifyNoMoreInteractions(clienteService);
//    }
//
//    @Test
//    public void save() throws Exception {
//        Mockito.when(clienteService.save(ArgumentMatchers.any(ClienteDto.class))).thenReturn(ClienteBuilder.getDto());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post(ENDPOINT_URL)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(ClienteBuilder.getDto())))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//        Mockito.verify(clienteService, Mockito.times(1)).save(ArgumentMatchers.any(ClienteDto.class));
//        Mockito.verifyNoMoreInteractions(clienteService);
//    }
//
////    @Test
////    public void update() throws Exception {
////        Mockito.when(clienteService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(ClienteBuilder.getDto());
////
////        mockMvc.perform(
////                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(CustomUtils.asJsonString(ClienteBuilder.getDto())))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        Mockito.verify(clienteService, Mockito.times(1)).update(ArgumentMatchers.any(ClienteDto.class), ArgumentMatchers.anyLong());
////        Mockito.verifyNoMoreInteractions(clienteService);
////    }
//
//    @Test
//    public void delete() throws Exception {
//        Mockito.doNothing().when(clienteService).deleteById(ArgumentMatchers.anyLong());
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(CustomUtils.asJsonString(ClienteBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(clienteService, Mockito.times(1)).deleteById(Mockito.anyLong());
//        Mockito.verifyNoMoreInteractions(clienteService);
//    }
//}