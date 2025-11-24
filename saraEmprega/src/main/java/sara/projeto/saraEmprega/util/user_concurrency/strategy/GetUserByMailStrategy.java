// package sara.projeto.saraEmprega.util.user_concurrency.strategy;

// import lombok.RequiredArgsConstructor;
// import sara.projeto.saraEmprega.enums.UserAction;
// import sara.projeto.saraEmprega.model.User;
// import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperation;
// import org.springframework.stereotype.Component;

// @Component
// @RequiredArgsConstructor
// public class GetUserByMailStrategy implements UserOperationStrategy<UserOperation> {
//     private final UserServicePort userServicePort;

//     @Override
//     public User execute(UserOperation operation) {
//         String mail = operation.getMail();
//         return userServicePort.getUserByMail(mail);
//     }

//     @Override
//     public UserAction getUserAction() {
//         return UserAction.GET_USER_BY_MAIL;
//     }
// }
