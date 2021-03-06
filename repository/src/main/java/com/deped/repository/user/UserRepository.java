package com.deped.repository.user;

import com.deped.exceptions.DatabaseRolesViolationException;
import com.deped.model.account.User;
import com.deped.model.security.PasswordResetToken;
import com.deped.repository.BaseRepository;

/**
 * Created by PlanetClick on 3/9/2017.
 */
public interface UserRepository extends BaseRepository<User, String> {
    User loginUser(User user, LoginMethod loginMethod);

    User fetchByUsername(String username);

    User fetchByEmail(String email);

    PasswordResetToken createPasswordResetTokenForUser(PasswordResetToken passwordResetToken) throws DatabaseRolesViolationException;

    Boolean removePasswordResetToken(PasswordResetToken passwordResetToken);

    PasswordResetToken findByToken(String token);

    boolean changePasswordByToken(String username, String token, String newPassword, int period) throws DatabaseRolesViolationException;
}
