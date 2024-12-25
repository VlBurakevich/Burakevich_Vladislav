package org.senla.dao;

import org.senla.dao.quires.MemberQueries;
import org.senla.entity.Member;
import org.senla.enums.GenderType;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.MemberNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDao extends BaseDao{
    @Override
    public Member getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(MemberQueries.GET_BY_ID)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToMember(resultSet);
            } else {
                throw new MemberNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get member by ID.", e);
        }
    }

    @Override
    public List<Member> getAll() {
        List<Member> members = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(MemberQueries.GET_ALL)) {

            while (resultSet.next()) {
                members.add(mapToMember(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get members.", e);
        }

        return members;
    }

    @Override
    public void insert(Object entity) {
        Member member = (Member) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MemberQueries.INSERT)) {
                statement.setLong(1, member.getId());
                statement.setString(2, member.getFirstName());
                statement.setString(3, member.getLastName());
                statement.setString(4, member.getNationality());
                statement.setString(5, member.getGender().toString());

                statement.execute();

                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    member.setId(resultSet.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert member.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        Member member = (Member) entity;

        executeTransaction(connection -> {
           try (PreparedStatement statement = connection.prepareStatement(MemberQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
               statement.setString(1, member.getFirstName());
               statement.setString(2, member.getLastName());
               statement.setString(3, member.getNationality());
               statement.setString(4, member.getGender().toString());
               statement.setLong(5, member.getId());
               statement.executeUpdate();

           } catch (SQLException e) {
               throw new DatabaseException("Failed to update member ", e);
           }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MemberQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete member ", e);
            }
        });
    }

    private Member mapToMember(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setFirstName(resultSet.getString("first_name"));
        member.setLastName(resultSet.getString("last_name"));
        member.setNationality(resultSet.getString("nationality"));
        member.setGender(GenderType.valueOf(resultSet.getString("gender")));

        return member;
    }
}
