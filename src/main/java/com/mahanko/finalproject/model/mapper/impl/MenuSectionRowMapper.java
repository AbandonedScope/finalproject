package com.mahanko.finalproject.model.mapper.impl;

import com.mahanko.finalproject.exception.DaoException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.mapper.ColumnName;
import com.mahanko.finalproject.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The type MenuSectionRowMapper class. Maps result set to the MenuItem class object.
 */
public class MenuSectionRowMapper implements CustomRowMapper<MenuSection> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<MenuSection> map(ResultSet resultSet) throws DaoException {
        MenuSection section = new MenuSection();
        Optional<MenuSection> sectionOptional;
        try {
            section.setId(resultSet.getInt(ColumnName.SECTION_ID));
            section.setName(resultSet.getString(ColumnName.SECTION_NAME));
            resultSet.next();
            sectionOptional = Optional.of(section);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

        return sectionOptional;
    }
}
