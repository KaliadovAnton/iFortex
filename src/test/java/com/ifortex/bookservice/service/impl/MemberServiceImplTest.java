package com.ifortex.bookservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void testFindMember() {
        var expected = "Diana Prince";
        var result = memberService.findMember();

        assertEquals(expected, result.getName());
    }

    @Test
    void testFindMembers() {
        var expectedSize = 1;
        var expectedName = "Steve Rogers";
        var result = memberService.findMembers();

        assertEquals(expectedSize, result.size());
        assertEquals(expectedName, result.get(0).getName());
    }
}