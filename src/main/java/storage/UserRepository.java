package storage;

import domain.User;
import domain.UserType;
import org.graalvm.compiler.lir.LIRInstruction;

public interface UserRepository extends CrudRepository<User, String> {
}
