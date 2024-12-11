package br.com.aplicacao.core.dto;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ResponseBodyDtoTest {

    @Test
    public void test_store_and_retrieve_entidade() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto("Test Entity");
        assertEquals("Test Entity", responseBodyDto.getEntidade());
    }

    @Test
    public void test_handle_different_types_of_entidade() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(123);
        assertEquals(123, responseBodyDto.getEntidade());

        responseBodyDto.setEntidade(45.67);
        assertEquals(45.67, responseBodyDto.getEntidade());

        responseBodyDto.setEntidade(true);
        assertEquals(true, responseBodyDto.getEntidade());
    }

    @Test
    public void test_initialize_entidade_through_constructor() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto("Initial Entity");
        assertEquals("Initial Entity", responseBodyDto.getEntidade());
    }

    @Test
    public void test_update_entidade_object() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto("Old Entity");
        responseBodyDto.setEntidade("New Entity");
        assertEquals("New Entity", responseBodyDto.getEntidade());
    }

    @Test
    public void test_equals_and_hashCode_methods() {
        ResponseBodyDto dto1 = new ResponseBodyDto("Entity");
        ResponseBodyDto dto2 = new ResponseBodyDto("Entity");
        assertTrue(dto1.equals(dto2) && dto2.equals(dto1));
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void test_handle_null_entidade() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(null);
        assertNull(responseBodyDto.getEntidade());
    }

    @Test
    public void test_handle_complex_nested_objects_as_entidade() {
        Map<String, List<Integer>> complexObject = new HashMap<>();
        complexObject.put("key", Arrays.asList(1, 2, 3));
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(complexObject);
        assertEquals(complexObject, responseBodyDto.getEntidade());
    }

    @Test
    public void test_handle_large_data_sets_as_entidade() {
        List<Integer> largeDataSet = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            largeDataSet.add(i);
        }
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(largeDataSet);
        assertEquals(largeDataSet, responseBodyDto.getEntidade());
    }

    @Test
    public void test_handle_special_characters_as_entidade() {
        String specialChars = "!@#$%^&*()_+";
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(specialChars);
        assertEquals(specialChars, responseBodyDto.getEntidade());
    }

    @Test
    public void test_handle_empty_collections_as_entidade() {
        List<String> emptyList = new ArrayList<>();
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(emptyList);
        assertEquals(emptyList, responseBodyDto.getEntidade());

        Map<String, String> emptyMap = new HashMap<>();
        responseBodyDto.setEntidade(emptyMap);
        assertEquals(emptyMap, responseBodyDto.getEntidade());
    }

    @Test
    public void test_toString_method() {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto("Entity");
        String expectedString = "ResponseBodyDto(entidade=Entity)";
        assertEquals(expectedString, responseBodyDto.toString());
    }
}
