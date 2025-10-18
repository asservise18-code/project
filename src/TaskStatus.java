public enum TaskStatus {
    COMPLETED("Выполнена"),
    IN_PROGRESS("В процессе"),
    NOT_COMPLETED("Не выполнена");

    private final String description;

    TaskStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
