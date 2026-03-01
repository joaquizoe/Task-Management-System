//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Database database = new Database();
    User user = new User(database);
    user.createAccount("Joaqui", "Santiago", "Said123-");
    database.close();
}

