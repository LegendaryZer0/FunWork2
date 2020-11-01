package repository;

import model.User;
import repository.utill.RowMapper;
import repository.utill.SimpleDataSource;
import repository.utill.SimpleJdbcTemplate;

import java.sql.DriverManager;
import java.util.List;

public class UserRepository {
    private SimpleJdbcTemplate simpleJdbcTemplate;

    //language=PostgreSQL
    private final String SQL_CREATE_USER="INSERT INTO \"User\" values (?,?,?)";
    //language=PostgreSQL
    private final String SQL_UPDATE_USER ="UPDATE  \"User\" SET id =?,name =?,age=?";
    //language=PostgreSQL
    private final String SQL_GET_USER_BY_ID="select * from \"User\" where id=?";
    //language=PostgreSQL
    private final String SQL_GET_ALL_USERS="select  * from \"User\"";
    //language=PostgreSQL
    private final String SQL_DELETE_USER_BY_ID="DELETE FROM \"User\" where id=?";

    public UserRepository(){
        simpleJdbcTemplate = new SimpleJdbcTemplate(new SimpleDataSource("jdbc:postgresql://localhost:5432/FabulousDatabase","postgres","123456789"));

    }
    private final RowMapper<User>  userRowMapper=row -> User.builder().id(row.getLong("id"))
            .name(row.getString("name"))
            .age(row.getInt("age")).build();
    public void create(User user){
        simpleJdbcTemplate.query(SQL_CREATE_USER,userRowMapper,user.getId(),user.getName(),user.getAge());
    }
    public void update(User user){
        simpleJdbcTemplate.query(SQL_UPDATE_USER,userRowMapper,user.getId(),user.getName(),user.getAge());
    }
    public User get(User user){
        return simpleJdbcTemplate.query(SQL_GET_USER_BY_ID,userRowMapper,user.getId()).get(0);
    }

    public List<User> getAll(){
        return simpleJdbcTemplate.query(SQL_GET_ALL_USERS,userRowMapper);
    }
    public void delete(User user){
        simpleJdbcTemplate.query(SQL_DELETE_USER_BY_ID,userRowMapper,user.getId());
    }


}
