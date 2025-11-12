# Backend - TurismoUY Web Services

Módulo backend del sistema TurismoUY. Proporciona:
- **Web Services SOAP** (JAX-WS 4.0.3 Metro) publicados en puerto 8007
- **Lógica de negocio** con patrón Controller/Handler
- **Persistencia JPA** usando EclipseLink contra HSQLDB
- **Interfaz Swing** para aplicación de escritorio (opcional)

## Propósito

El backend expone tres Web Services SOAP que el frontend invocar remotamente via wsimport-generated stubs:

| Servicio | WSDL URL | Puerto | Responsabilidad |
|----------|----------|--------|-----------------|
| **UserService** | `http://localhost:8007/ws/user?wsdl` | 8007 | Gestión de usuarios, turistas, proveedores |
| **ActivityService** | `http://localhost:8007/ws/activity?wsdl` | 8007 | Gestión de actividades turísticas |
| **OutingAndInscriptionService** | `http://localhost:8007/ws/outingAndInscription?wsdl` | 8007 | Salidas e inscripciones de turistas |

**Nota**: No es un monolito JAR incluido en frontend. Es una arquitectura con **comunicación remota SOAP**.

## Estructura

```
src/main/java/
├── desktop/                              # Interfaz Swing (opcional)
│   ├── Main.java                        # Punto de entrada GUI
│   └── Formularios*.java                # Varias ventanas Swing
│
└── turismouyapp/core/
    ├── webservices/                     # ⚠️ Expone servicios SOAP
    │   ├── IUserWebService.java          # Interfaz SOAP (@WebService)
    │   ├── UserWebService.java           # Implementación SOAP
    │   ├── IActivityWebService.java
    │   ├── ActivityWebService.java
    │   ├── IOutingAndInscriptionWebService.java
    │   ├── OutingAndInscriptionWebService.java
    │   └── Publisher.java                # ⚠️ MAIN: Publica servicios en 8007
    │
    ├── controller/                      # Lógica de negocio
    │   ├── UserController.java
    │   ├── TouristActivityController.java
    │   └── TouristOutingAndInscriptionController.java
    │
    ├── handler/                         # DAO/Persistencia
    │   ├── UserHandler.java
    │   ├── TouristActivityHandler.java
    │   └── TouristOutingAndInscriptionHandler.java
    │
    ├── entity/                          # Entidades JPA (@Entity)
    │   ├── User.java
    │   ├── Tourist.java
    │   ├── Supplier.java
    │   ├── TouristActivity.java
    │   ├── TouristOuting.java
    │   └── Inscription.java
    │
    ├── dto/                             # Data Transfer Objects (@XmlRootElement)
    │   ├── DtUser.java
    │   ├── DtTourist.java
    │   ├── DtSupplier.java
    │   └── ...
    │
    ├── factory/                         # Inyección de dependencias
    │   └── FactoryUyTourism.java
    │
    ├── exceptions/                      # Excepciones de negocio (@WebFault)
    │   ├── BusinessException.java
    │   └── RepeatedUserNicknameException.java
    │
    └── db/                              # Base de datos
        └── HsqldbServerListener.java    # Listener Tomcat para HSQLDB
```

## Compilación

```bash
# Compilar backend (genera turismouy.Backend-1.0.0.jar)
cd backend
mvn clean install

# Solo compilar sin tests
mvn clean install -DskipTests
```

## Ejecución

### Opción 1: Publisher desde Eclipse (RECOMENDADO)

1. Click derecho en proyecto `turismouy.Backend`
2. **Run → Run Configurations...**
3. **New Java Application**:
   - Main class: `turismouyapp.webservices.Publisher`
4. **Run** → Verás endpoints publicados en localhost:8007

### Opción 2: Terminal

```bash
# En terminal separada
cd backend
mvn exec:java -Prun-publisher

# Salida esperada:
# [UserWebService] http://localhost:8007/ws/user
# [ActivityWebService] http://localhost:8007/ws/activity
# [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription
```

**⚠️ Mantener corriendo mientras compiles frontend** (wsimport lo necesita).

### Opción 3: GUI Swing (Escritorio)

```bash
# Desde Eclipse
1. Abrir desktop/Main.java
2. Click derecho → Run As → Java Application

# Desde terminal (requiere HSQLDB en puerto 9001)
cd backend
java -jar target/turismouy.Backend-1.0.0.jar
```

## Arquitectura de Web Services

### Flujo de Invocación

```
Frontend Servlet
       ⬇
   (activityService.listTouristActivities())  ← Generated stub
       ⬇
   SOAP Call over HTTP (localhost:8007)
       ⬇
Backend Publisher (port 8007)
       ⬇
ActivityWebService.java
       ⬇
TouristActivityController.java
       ⬇
TouristActivityHandler.java
       ⬇
JPA Entity Manager
       ⬇
HSQLDB (port 9001)
```

### Interfaces SOAP

Cada interfaz tiene anotaciones JAX-WS:

```java
@WebService(name = "UserPortType", targetNamespace = "http://ws.turismouyapp/schema")
@SOAPBinding(style = Style.RPC)
public interface IUserWebService {
    
    @WebMethod
    @WebResult(name = "DtUser")
    DtUser consultUserData(@WebParam(name = "nickname") String nickname) 
        throws BusinessException;
    
    // Más métodos...
}
```

### Implementaciones SOAP

```java
@WebService(name = "UserPortType", 
            endpointInterface = "turismouyapp.webservices.interfaces.IUserWebService",
            targetNamespace = "http://ws.turismouyapp/schema",
            serviceName = "UserService")
public class UserWebService implements IUserWebService {
    
    private UserController userController;
    
    public UserWebService() {
        this.userController = FactoryUyTourism.getInstance().getUserController();
    }
    
    @Override
    public DtUser consultUserData(String nickname) throws BusinessException {
        return userController.getUserData(nickname);
    }
    
    // Más métodos...
}
```

### DTOs con JAXB

Los DTOs tienen anotaciones JAXB para serialización XML:

```java
@XmlRootElement
@XmlType(name = "DtUser", namespace = "http://ws.turismouyapp/schema")
public class DtUser {
    
    @XmlElement
    private String nickname;
    
    @XmlElement
    private String email;
    
    // getters/setters
}
```

## Base de Datos

### Persistencia JPA

Configurado en `src/main/resources/META-INF/persistence.xml`:

```xml
<persistence-unit name="turismoUyDB-Server" transaction-type="RESOURCE_LOCAL">
  <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
  <properties>
    <property name="jakarta.persistence.jdbc.driver" value="org.hsqldb.jdbc.JDBCDriver"/>
    <property name="jakarta.persistence.jdbc.url" value="jdbc:hsqldb:hsql://localhost:9001/turismoUyDB"/>
    <property name="jakarta.persistence.jdbc.user" value="SA"/>
    <property name="jakarta.persistence.jdbc.password" value=""/>
    <property name="eclipselink.ddl-generation" value="create-or-extend-tables"/>
  </properties>
</persistence-unit>
```

**Conecta a**: `localhost:9001` (iniciado automáticamente por Tomcat listener)

### Entidades

- **User**: Usuarios base (abstract)
  - **Tourist**: Turista (extiende User)
  - **Supplier**: Proveedor (extiende User)
- **TouristActivity**: Actividades turísticas ofrecidas
- **TouristOuting**: Salidas turísticas programadas
- **Inscription**: Inscripciones de turistas a salidas

## Dependencias Clave

| Dependencia | Versión | Propósito |
|------------|---------|-----------|
| `jakarta.ws.rs:jakarta.ws.rs-api` | 3.1.0 | JAX-WS (Web Services SOAP) |
| `com.sun.xml.ws:jaxws-rt` | 4.0.3 | Metro runtime |
| `jakarta.persistence:jakarta.persistence-api` | 3.1.0 | JPA |
| `org.eclipse.persistence:eclipselink` | 4.0.4 | Implementación JPA |
| `org.hsqldb:hsqldb` | 2.7.2 | Base de datos |
| `org.apache.tomcat.catalina:catalina` | 11.0.11 | Listener para Tomcat |

## Documentación

Generar Javadoc:

```bash
cd backend
mvn javadoc:javadoc

# Abrir: target/site/apidocs/index.html
```

## Troubleshooting

**Error**: `Address already in use: 127.0.0.1:8007`
- Solución: Ya hay un Publisher corriendo. Matarlo: `killall java` (Mac/Linux) o `taskkill /F /IM java.exe` (Windows)

**Error**: `NullPointerException en FactoryUyTourism`
- Causa: Database no está accesible en localhost:9001
- Solución: Iniciar Tomcat primero (incluye HSQLDB listener)

**Error**: `PersistenceException: No persistence unit named`
- Causa: persistence.xml no encontrado o mal configurado
- Verificar: `src/main/resources/META-INF/persistence.xml` debe existir

## Referencias

- [JAX-WS (Web Services)](https://jakarta.ee/specifications/webservices/)
- [Metro (RI)](https://github.com/eclipse-ee4j/metro-jax-ws)
- [Jakarta Persistence (JPA)](https://jakarta.ee/specifications/persistence/)
- [EclipseLink](https://eclipse.dev/eclipselink/)
- [HSQLDB Documentation](http://hsqldb.org/doc/2.0/guide/)
