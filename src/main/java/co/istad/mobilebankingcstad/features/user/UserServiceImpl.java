package co.istad.mobilebankingcstad.features.user;


import co.istad.mobilebankingcstad.domain.Role;
import co.istad.mobilebankingcstad.domain.User;
import co.istad.mobilebankingcstad.features.roles.RoleRepository;
import co.istad.mobilebankingcstad.features.user.dto.UserRequest;
import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import co.istad.mobilebankingcstad.features.user.dto.UserUpdateRequest;
import co.istad.mobilebankingcstad.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // check if username and email already exist !
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exist ! Try another one ");
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already token ! Try another one ");
        }


        Set<Role> roles = new HashSet<>();
        for (var role : userRequest.roles()) {
            var roleObj = roleRepository.findByName(role)
                    .orElseThrow(
                            () -> new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST,
                                    "Role: <" + role + "> could not found!"
                            )
                    );
            roles.add(roleObj);
        }

        User newUser = userMapper.requestToUser(userRequest);
        newUser.setBlocked(false);
        newUser.setDeleted(false);
        newUser.setRoles(roles);
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no user with id = " + id));
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no user with id = " + id));
        userRepository.delete(user);

    }

    /*partial update */
    @Override
    public UserResponse updateUserById(String id, UserUpdateRequest userRequest) {
        var updateUser = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("There is no user with = " + id));
        userMapper.updateUserFromRequest(updateUser, userRequest);
        return userMapper.toUserResponse(userRepository.save(updateUser));
    }


//    will improvise this later bases on the logic of the services
    @Override
    public UserResponse disableUser(String id) {
        int affectedRow = userRepository.updateBlockedStatusById(id, true);
        if (affectedRow > 0) {
            return userMapper.toUserResponse(
                    userRepository.findById(id)
                            .orElse(null));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with id = " + id + " doesn't exist ! "
            );
        }

    }
    @Override
    public UserResponse enableUser(String id) {
        int affectedRow = userRepository.updateBlockedStatusById(id, false);
        if (affectedRow > 0) {
            return userMapper.toUserResponse(
                    userRepository.findById(id)
                            .orElse(null));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with id = " + id + " doesn't exist ! "
            );
        }
    }

}
