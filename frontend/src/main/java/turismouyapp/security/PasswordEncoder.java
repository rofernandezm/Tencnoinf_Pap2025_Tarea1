package turismouyapp.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    
    /**
     * Factor de trabajo BCrypt.
     */
    private static final int BCRYPT_ROUNDS = 12;
    
    private PasswordEncoder() {
    }
    
    /**
     * Codifica una contraseña en texto plano usando BCrypt.
     * 
     * <p>Genera un hash de 60 caracteres con formato: {@code $2a$12$[salt][hash]}</p>
     * 
     * @param rawPassword Contraseña en texto plano
     * @return Hash BCrypt de 60 caracteres
     * @throws IllegalArgumentException si rawPassword es null o vacío
     */
    public static String encode(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser null o vacía");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    /**
     * Verifica si una contraseña coincide con un hash BCrypt.
     * 
     * @param rawPassword Contraseña en texto plano
     * @param encodedPassword Hash BCrypt almacenado
     * @return {@code true} si coincide, {@code false} en cualquier otro caso
     */
    @SuppressWarnings("unused")
	public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.isEmpty() || 
            encodedPassword == null || encodedPassword.isEmpty()) {
            return false;
        }
        
        if (!encodedPassword.startsWith("$2a$") && !encodedPassword.startsWith("$2b$")) {
            return false;
        }
        
        try {
            // Debugging logs
        	if (1 == 1) {
	        	System.out.println("PasswordEncoder.matches :: ByCrypt.checkpw :: "+ BCrypt.checkpw(rawPassword, encodedPassword));
	        	System.out.println("PasswordEncoder.matches :: rawPassword :: "+ rawPassword);
	        	System.out.println("PasswordEncoder :: New RawEncoded :: "+ PasswordEncoder.encode(rawPassword));
	        	System.out.println("PasswordEncoder.matches :: encodedPassword :: "+ encodedPassword);
        	}
            return BCrypt.checkpw(rawPassword, encodedPassword);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Verifica si un string es un hash BCrypt válido.
     * 
     * @param hash String a validar
     * @return {@code true} si es un hash BCrypt válido (formato y longitud)
     */
    public static boolean isBCryptHash(String hash) {
        if (hash == null || hash.length() != 60) {
            return false;
        }
        return hash.startsWith("$2a$") || hash.startsWith("$2b$");
    }
}