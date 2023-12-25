package dao;

import entity.Role;

import java.util.List;

public class RoleImpl implements IRole{

    @Override
    public int addUser(Role role) {
        return 0;
    }

    @Override
    public int getExistingRoleIdFromRoleTable() {
        return 0;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        return false;
    }

    @Override
    public void ajouterUser() {

    }

    @Override
    public void connexion() {

    }

    @Override
    public List<Role> list() {
        return null;
    }

    @Override
    public Role get(int id) {
        return null;
    }
}
