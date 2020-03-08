package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.ItemService;
import com.gmail.vladbaransky.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    public String getItem(Model model, @ModelAttribute(name = "item") ItemDTO item) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList = itemService.showItemsById(item);
        model.addAttribute("items", itemDTOList);
        return "item";
    }

    @GetMapping("/item/add")
    public String addItemPage(Model model) {
        model.addAttribute("item", new ItemDTO());
        return "itemAdd";
    }

    @PostMapping("/item/add")
    public String addItem(Model model, @ModelAttribute(name = "item") ItemDTO item
    ) {
        itemService.addItem(item);
        return "redirect:/item";
    }

    @GetMapping("/item/delete")
    public String deleteItemPage(Model model) {
        model.addAttribute("item", new ItemDTO());
        return "itemDelete";
    }

    @PostMapping("/item/delete")
    public String deleteItem(Model model, @ModelAttribute(name = "item") ItemDTO item) {

        itemService.delete(item);
        return "itemDelete";
    }
}
