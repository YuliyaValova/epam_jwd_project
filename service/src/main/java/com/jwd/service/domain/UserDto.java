package com.jwd.service.domain;

import com.jwd.dao.domain.User;
import com.jwd.dao.domain.UserAccount;

public class UserDto {
    private long id;
    private String login;
    private User user;

    public UserDto() {
    }

    public UserDto(long id, String login, User user) {
        this.id = id;
        this.login = login;
        this.user = user;
    }

      public UserDto(UserAccount userAcc){
        this.id = userAcc.getId();
        this.login = userAcc.getLogin();
        this.user = userAcc.getUser();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != userDto.id) return false;
        if (login != null ? !login.equals(userDto.login) : userDto.login != null) return false;
        return user != null ? user.equals(userDto.user) : userDto.user == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", user=" + user +
                '}';
    }
}
