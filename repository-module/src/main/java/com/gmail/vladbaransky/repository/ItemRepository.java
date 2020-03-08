package com.gmail.vladbaransky.repository;

import com.gmail.vladbaransky.repository.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemRepository {
    List<Item> findItemById(Connection connection, Item item) throws SQLException;

    void add(Connection connection, Item item);

    void delete(Connection connection, Item item);
}
