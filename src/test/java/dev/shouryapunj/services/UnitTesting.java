package dev.shouryapunj.services;

import dev.shouryapunj.entity.Menu;
import dev.shouryapunj.repository.MenuRepository;
import dev.shouryapunj.service.MenuService;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@RunWith(SpringRunner.class)
public class UnitTesting {

    @MockBean
    MenuRepository menuRepository;

    @Autowired
    MenuService menuService;

    @Test
    public void findAll() throws Exception {
        Menu menu = new Menu("food-A", 10.0, ZonedDateTime.now(), ZonedDateTime.now());

        List<Menu> menuList = Collections.singletonList(menu);
        Mockito.when(menuRepository.findAll())
                .thenReturn(menuList);

        List<Menu> result = Collections.singletonList(menuService.findMenuByName("food-A").get());
        Assert.assertEquals("Menu should match", menuList, result);
    }


}
