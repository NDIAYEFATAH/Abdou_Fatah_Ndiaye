package dao;

import java.util.List;

public interface IRepertory<T> {
    public int addUser(T t);

    int getExistingRoleIdFromRoleTable();
    public boolean authenticateUser(String email, String password);
    public void ajouterUser();
    public void connexion();
    public List<T> list();
    public T get(int id);
}
