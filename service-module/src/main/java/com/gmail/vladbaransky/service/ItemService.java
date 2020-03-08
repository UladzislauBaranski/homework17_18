package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.model.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> showItemsById(ItemDTO item);

    void addItem(ItemDTO item);

    void delete(ItemDTO item);
}
