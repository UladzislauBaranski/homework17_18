package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.repository.ConnectionRepository;
import com.gmail.vladbaransky.repository.ItemRepository;
import com.gmail.vladbaransky.repository.model.Item;
import com.gmail.vladbaransky.service.ItemService;
import com.gmail.vladbaransky.service.model.ItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private ItemRepository itemRepository;

    public ItemServiceImpl(ConnectionRepository connectionRepository, ItemRepository itemRepository) {
        this.connectionRepository = connectionRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDTO> showItemsById(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToObject(itemDTO);
                List<Item> people = itemRepository.findItemById(connection, item);
                List<ItemDTO> userDTOList = people.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void addItem(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToObject(itemDTO);
                itemRepository.add(connection, item);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToObject(itemDTO);
                itemRepository.delete(connection, item);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                //  logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            // logger.error(e.getMessage(), e);
        }
    }

    private ItemDTO convertObjectToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setUniqueNumber(item.getUniqueNumber());
        return itemDTO;
    }

    private Item convertDTOToObject(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setDescription(itemDTO.getDescription());
        return item;
    }
}
