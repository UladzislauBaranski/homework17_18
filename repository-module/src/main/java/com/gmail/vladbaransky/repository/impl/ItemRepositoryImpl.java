package com.gmail.vladbaransky.repository.impl;

import com.gmail.vladbaransky.repository.ItemRepository;
import com.gmail.vladbaransky.repository.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public List<Item> findItemById(Connection connection, Item item) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT  unique_number, description FROM item where id=? "
                )
        ) {
            preparedStatement.setInt(1, item.getId());
            List<Item> items = new ArrayList<>();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    item.setId(id);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public void add(Connection connection, Item item) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO item(id, uniqu_number, description) VALUES (?,?,?)"
        )) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setInt(2, item.getUniqueNumber());
            preparedStatement.setString(3, item.getDescription());
            int rowAffected = preparedStatement.executeUpdate();
            logger.info("Row affected:add " + rowAffected);
        } catch (SQLException e) {
            logger.error("Failed to insert item");
        }
    }

    @Override
    public void delete(Connection connection, Item item) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM item WHERE id =?"
        )) {
            preparedStatement.setInt(1, item.getId());
            int rowAffected = preparedStatement.executeUpdate();
            logger.info("Row affected:" + rowAffected);
        } catch (SQLException e) {
            logger.info("Failed to delete item");
        }
    }
}
