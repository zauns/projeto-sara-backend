// package sara.projeto.saraEmprega.util.user_concurrency.strategy;

// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Component;
// import sara.projeto.saraEmprega.dto.UserDTO;
// import sara.projeto.saraEmprega.enums.UserAction;
// import sara.projeto.saraEmprega.model.User;
// import sara.projeto.saraEmprega.service.UserService;
// import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperationUpdate;

// @Component
// @RequiredArgsConstructor
// public class UpdateUserStrategy implements UserOperationStrategy<UserOperationUpdate> {

//     private final UserService userService;

//     @Override
//     public User execute(UserOperationUpdate op) {
//         UserDTO userDTO = op.getDto();
//         String mail = op.getMail();
//         return userService.updateUser(userDTO, mail);
//     }

//     @Override
//     public UserAction getUserAction() {
//         return UserAction.UPDATE_USER;
//     }
// }
