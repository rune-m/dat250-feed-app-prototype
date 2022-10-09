package group14.feedapp.service;

import group14.feedapp.model.User;

public interface IAuthService {

    User getAuthorizedUser(String token);
}
