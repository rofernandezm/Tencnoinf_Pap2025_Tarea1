package turismouyapp.core.dto;

public enum SessionState {
    NO_LOGIN,           // nunca intentó iniciar sesión
    LOGIN_SUCCESS,     // tiene la sesión iniciada
    LOGIN_UNSUCCESSFUL    // le erro a la sesión al menos una vez
}