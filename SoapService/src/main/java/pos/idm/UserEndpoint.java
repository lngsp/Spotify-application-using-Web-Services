package pos.idm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import pos.idm.Entity.Roles;
import pos.idm.Entity.Users;
import pos.idm.Service.RolesService;
import pos.idm.Service.UsersService;
import soap.pos.idm.*;  //generated classes ar in target.generated-sources.jaxb.soap.pos.idm.**

import java.util.ArrayList;
import java.util.List;


@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://pos.soap/idm";

    @Autowired
    private final UsersService usersService;

    @Autowired
    private final RolesService rolesService;


    public UserEndpoint(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

////////////       LOGIN
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public ResponseEntity<LoginResponse> login(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if(request.getUsername().length() == 0 || request.getPassword().length() == 0){ //username ul nu exista in baza de date
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The username and the password cannot be empty!");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.LENGTH_REQUIRED);

        } else if (usersService.isUsernameInDb(request.getUsername()) == false) {
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The user doens't exist!");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        } else if(usersService.getPassword(request.getUsername()) != request.getPassword()) {
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The username or the password is incorect!");
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);

        }else{
            response.setServiceStatus(serviceStatus);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User is corect. You can login!");
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

////////////       USERS
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(){
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<UserInfo> userInfoList =  new ArrayList<>();
        List<Users> userList = usersService.getAllUser();
        for(int i = 0; i < userList.size(); i++){
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userList.get(i), userInfo);
            userInfoList.add(userInfo);
        }
        response.getUserInfo().addAll(userInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public ResponseEntity<GetUserByIdResponse> getUser(@RequestPayload GetUserByIdRequest request){
        GetUserByIdResponse response = new GetUserByIdResponse();
        UserInfo userInfo = new UserInfo();
        ServiceStatus serviceStatus = new ServiceStatus();

        if(usersService.getUserById(request.getId()) == null){
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("User doesn't exit!");
            response.setUserInfo(null);
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            Users users = usersService.getUserById(request.getId());
            Roles roles = usersService.getUserRoles(request.getId());   // returneaza rolul user-ului
            RoleInfo roleInfo = new RoleInfo();

            BeanUtils.copyProperties(roles, roleInfo);  // copiem rolul in roleInfo pentru a putea seta UserInfo

            userInfo.setId(users.getId());
            userInfo.setPassword(users.getPassword());
            userInfo.setUsername(users.getUsername());
            userInfo.setRole(roleInfo);

            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User has been returned!");

            response.setUserInfo(userInfo);
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public ResponseEntity<AddUserResponse> addUser(@RequestPayload AddUserRequest request){
        AddUserResponse response = new AddUserResponse();
        UserInfo userInfo = request.getUserInfo();
        ServiceStatus serviceStatus = new ServiceStatus();

        boolean existed = usersService.isUsernameInDb(userInfo.getUsername());

        if(userInfo.getPassword() == null){
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("Password cannot be null!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.LENGTH_REQUIRED);
        }
        else if(existed == true){
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The username is already added!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.CONFLICT);
        }
        else if(userInfo.getUsername().length() < 5){
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The username must have at least 5 characters!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.LENGTH_REQUIRED);
        }
        else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Added new user!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUsernameRequest")
    @ResponsePayload
    public ResponseEntity<UpdateUsernameResponse> updateUsername(@RequestPayload UpdateUsernameRequest request) {
        UpdateUsernameResponse response = new UpdateUsernameResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if(usersService.isUsernameInDb(request.getOldUsername()) == false){ //username ul nu exista in baza de date
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The user doens't exist!");
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else {
            usersService.updateUsername(request.getOldUsername(), request.getNewUsername());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Update the user with the old username " + request.getOldUsername() + "!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePasswordRequest")
    @ResponsePayload
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestPayload UpdatePasswordRequest request) {
        UpdatePasswordResponse response = new UpdatePasswordResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if(usersService.isUsernameInDb(request.getUsername()) == false){ //username ul nu exista in baza de date
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The user doens't exist!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else {
            usersService.updatePassword(request.getUsername(), request.getNewPassword());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Update the password for the user with the username " + request.getUsername() + "!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public ResponseEntity<DeleteUserResponse> deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if(usersService.getUserById(request.getId()) == null){   // nu exista acest user in db
            serviceStatus.setStatusCode("ERROR");
            serviceStatus.setMessage("The user doesn't exist in the data base!");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else {
            usersService.deleteUser(request.getId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("The user has been deleted");
            response.setServiceStatus(serviceStatus);
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
        }
    }

    ////////////       ROLES

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllRolesRequest")
    @ResponsePayload
    public GetAllRolesResponse getAllRoles() {
        GetAllRolesResponse response = new GetAllRolesResponse();
        List<RoleInfo> roleInfoList =  new ArrayList<>();

        List<Roles> rolesList = rolesService.getAllRoles();

        for(int i = 0; i < rolesList.size(); i++){
            RoleInfo roleInfo = new RoleInfo();
            BeanUtils.copyProperties(rolesList.get(i), roleInfo);
            roleInfoList.add(roleInfo);
        }
        //  response.getRoleInfo().addAll(roleInfoList);            <-- Eroare
        return response;
    }

}
