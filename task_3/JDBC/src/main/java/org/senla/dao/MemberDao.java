package org.senla.dao;

import org.senla.dao.quires.CredentialQueries;
import org.senla.dao.quires.MemberQueries;
import org.senla.entity.Member;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.EntityNotFoundException;
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
    public Object getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(MemberQueries.GET_BY_ID)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToMember(resultSet);
            } else {
                throw new EntityNotFoundException("Member with id " + id + " not found");
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
    public void update(Object entity, Long id) {
        Member member = (Member) entity;

        executeTransaction(connection -> {
           try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.UPDATE)) {
               statement.setString(1, member.getFirstName());
               statement.setString(2, member.getLastName());
               statement.setString(3, member.getNationality());
               statement.setString(4, member.getGender().toString());
               statement.setLong(5, id);
               statement.executeUpdate();

           } catch (SQLException e) {
               throw new DatabaseException("Failed to update member with ID " + id, e);
           }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.DELETE)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete member with ID " + id, e);
            }
        });
    }

    private Member mapToMember(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getLong(1));
        member.setFirstName(resultSet.getString(2));
        member.setLastName(resultSet.getString(3));
        member.setNationality(resultSet.getString(4));

        return member;
    }
}
