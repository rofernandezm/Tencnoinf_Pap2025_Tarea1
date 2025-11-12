# 🌐 Web Services SOAP - Guía Completa

Este documento es un **suplemento al README.md** enfocado específicamente en los Web Services SOAP del proyecto TurismoUY.

---

## 📑 Tabla de Contenidos

- [Flujo Crítico de Ejecución](#flujo-crítico-de-ejecución)
- [Endpoints SOAP Disponibles](#endpoints-soap-disponibles)
- [Publicación de Web Services](#publicación-de-web-services)
- [Regeneración de Stubs (wsimport)](#regeneración-de-stubs-wsimport)
- [Arquitectura JAX-WS](#arquitectura-jax-ws)
- [Troubleshooting](#troubleshooting)
- [Referencias](#referencias)

---

## ⚡ Flujo Crítico de Ejecución

### ❌ INCORRECTO (Causará Errores)

```bash
# MALO: Compilar frontend directamente sin publisher
mvn clean install            # Compila backend + frontend

# RESULTADO: Frontend falla al buscar WSDLs
# [ERROR] Failed to download WSDL: http://localhost:8007/ws/user?wsdl
# [ERROR] Connection refused
```

### ✅ CORRECTO (Orden Exacto)

```bash
# PASO 1: Compilar Backend
mvn clean install

# PASO 2: Publicar Web Services (EN TERMINAL SEPARADA)
cd backend
mvn exec:java -Prun-publisher
# Espera estos logs:
# [UserWebService] http://localhost:8007/ws/user
# [ActivityWebService] http://localhost:8007/ws/activity
# [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription

# PASO 3: Compilar Frontend (CON Publisher Activo)
cd frontend
mvn clean package
# El plugin wsimport descarga WSDLs y genera stubs

# PASO 4: Iniciar Tomcat (EN OTRA TERMINAL)
cd server/apache-tomcat-11.0.11
./bin/startup.sh
```

### Visual del Flujo

```
┌─────────────────────────────────────────────────────────────┐
│                    DESARROLLO LOCAL                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  TERMINAL 1          TERMINAL 2          TERMINAL 3        │
│  ─────────────       ─────────────       ─────────────     │
│  Backend:8007        Frontend:compile    Tomcat:8080       │
│  (Publisher)         (wsimport)          (BD:9001)         │
│                                                             │
│  $ mvn exec:java     $ mvn clean         $ ./startup.sh   │
│    -Prun-publisher     package                            │
│                                                             │
│  ✅ ACTIVO           ✅ COMPILA          ✅ ACTIVO        │
│  (DEJAR ABIERTA)     (una sola vez)      (DEJAR ABIERTA)  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
            ⬆️                ⬆️                ⬆️
         Puerto             Usa WSDL        Llama WS
         8007               de 8007         en 8007
```

---

## 🔗 Endpoints SOAP Disponibles

### UserService

**URL**: `http://localhost:8007/ws/user?wsdl`  
**Interfaz**: `UserPortType`  
**Namespace**: `http://ws.turismouyapp/schema`

#### Métodos:

```java
// Consultar usuario
DtUser consultUserData(String nicknameOrEmail)
DtUser consultUserDataByEmail(String email)

// Registrar usuario
void dataEntryUser(DtUser dtUser)
void dataEntryTourist(DtTourist dtTourist)
void dataEntrySupplier(DtSupplier dtSupplier)

// Modificar usuario
void modifyUserData(DtUser dtUser)
void modifyTouristData(DtTourist dtTourist)
void modifySupplierData(DtSupplier dtSupplier)

// Otros
void confirmRegistration()
String[] listUsers()
void updateProfileImageUser(String nickname, String imageName)
```

**Excepciones**:
- `RepeatedUserEmailException`
- `RepeatedUserNicknameException`

---

### ActivityService

**URL**: `http://localhost:8007/ws/activity?wsdl`  
**Interfaz**: `ActivityPortType`  
**Namespace**: `http://ws.turismouyapp/schema`

#### Métodos:

```java
// Listar actividades
ArrayList<DtActivityWithOutings> listTouristActivityData()
String[] listTouristActivities()
String[] listTouristActivitiesByStatus(TouristActivityStatus status)
String[] listTouristActivitiesBySupplierNickname(String nickname)
ArrayList<DtActivityWithOutings> arrayListTouristActivitiesBySupplierNickName(String nickname)

// Consultar actividad
DtActivityWithOutings consultTouristActivityData(String activityName)

// Crear/Modificar
void activityDataEntry(DtTouristActivity dtTouristActivity)
void modifyActivity(DtTouristActivity dtTouristActivity)
```

**Excepciones**:
- `ActivityDoesNotExistException`
- `RepeatedActivityNameException`

---

### OutingAndInscriptionService

**URL**: `http://localhost:8007/ws/outingAndInscription?wsdl`  
**Interfaz**: `OutingAndInscriptionPortType`  
**Namespace**: `http://ws.turismouyapp/schema`

#### Métodos:

```java
// Salidas
void outingDataEntry(DtTouristOuting dtTouristOuting)
void updateOutingImageName(String outingName, String imageName)
DtTouristOuting consultTouristOutingData(String outingName)

// Inscripciones
void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, 
                          String userNickname, String outingName)
DtInscriptionTouristOuting[] listOutingInscription(String outingName)
ArrayList<DtInscriptionTouristOuting> listDtInscriptionTouristOutingByTouristNickname(String nickname)
```

**Excepciones**:
- `TouristOutingDoesNotExistException`
- `RepeatedTouristOutingException`
- `RepeatedInscriptionToTouristOutingException`

---

## 📤 Publicación de Web Services

### Desde Terminal (Recomendado)

```bash
cd backend

# Publicar (dejar terminal abierta)
mvn exec:java -Prun-publisher

# Verás logs como:
# [UserWebService] http://localhost:8007/ws/user
# [ActivityWebService] http://localhost:8007/ws/activity
# [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription
```

### Desde Eclipse

1. Crear **Run Configuration** nueva:
   - Type: **Java Application**
   - Project: `turismouy.Backend`
   - Main class: `turismouyapp.webservices.Publisher`
   - VM arguments: `-Dcom.sun.xml.ws.spi.db.BindingContextFactory=com.sun.xml.ws.db.glassfish.JAXBRIContextFactory`

2. Click **Run** → Ves logs en consola Eclipse

3. **NO CIERRES** - Mantén corriendo

### Verificar Publicación

```bash
# Comprobar que puerto 8007 escucha
netstat -ano | findstr :8007  # Windows
lsof -i :8007                  # Linux/macOS

# Descargar WSDL (desde otra terminal)
curl http://localhost:8007/ws/user?wsdl
# Debes ver XML con definición de servicio

# En navegador:
# http://localhost:8007/ws/user?wsdl
```

---

## 🔄 Regeneración de Stubs (wsimport)

### ¿Cuándo Regenerar Stubs?

1. ✅ **Después de cambios en Backend** (nuevos métodos en webservices)
2. ✅ **Después de cambios en DTOs** del backend
3. ✅ **Después de cambios en Excepciones** SOAP
4. ❌ **NO durante cambios en Frontend** (no es necesario)

### Regenerar Manualmente

```bash
cd frontend

# Compilar frontend (regenera automáticamente)
mvn clean package

# Los stubs se generan en:
# target/generated-sources/wsimport/turismouyapp/webservices/
```

### Ubicación de Stubs Generados

```
frontend/target/generated-sources/wsimport/turismouyapp/webservices/
├── UserService.java                    # Stub del servicio
├── UserPortType.java                   # Interfaz generada
├── ActivityService.java
├── ActivityPortType.java
├── OutingAndInscriptionService.java
├── OutingAndInscriptionPortType.java
├── DtUser.java                         # DTOs generados desde WSDL
├── DtTourist.java
├── DtSupplier.java
├── DtActivityWithOutings.java
├── DtTouristOuting.java
├── DtInscriptionTouristOuting.java
├── TouristActivityStatus.java
├── UserType.java
├── RepeatedUserEmailException.java     # Excepciones generadas
├── RepeatedUserNicknameException.java
├── ActivityDoesNotExistException.java
└── ... (más excepciones)
```

### Usar Stubs en Servlets

```java
import turismouyapp.webservices.UserService;
import turismouyapp.webservices.UserPortType;
import turismouyapp.webservices.DtUser;

public class ConsultUser extends HttpServlet {
    private final UserPortType userWebService;
    
    public ConsultUser() {
        // Los stubs se instancian así
        this.userWebService = new UserService().getUserPort();
    }
    
    protected void doPost(HttpServletRequest request, ...) {
        // Invocar método del servicio web
        DtUser user = userWebService.consultUserData(nickname);
        // ... resto de lógica
    }
}
```

---

## 🏗️ Arquitectura JAX-WS

### Estructura Backend

```
backend/src/main/java/turismouyapp/webservices/
├── interfaces/
│   ├── IUserWebService.java              # @WebService (interfaz)
│   ├── IActivityWebService.java          # Define contrato SOAP
│   └── IOutingAndInscriptionWebService.java
│
├── UserWebService.java                   # @WebService (implementación)
├── ActivityWebService.java               # Implementa interfaz
├── OutingAndInscriptionWebService.java   # Delega a controladores
│
├── Publisher.java                        # public static void main()
│   # Instancia servicios y llama .publicar()
│
└── utils/
    └── DateUtils.java                    # Utilidades para fechas
```

### Anotaciones Usadas

```java
// En Interfaz:
@WebService(targetNamespace = "http://ws.turismouyapp/schema", name = "UserPortType")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
public interface IUserWebService {
    @WebMethod
    @WebResult(name = "DtUser")
    DtUser consultUserData(@WebParam(name = "nicknameOrEmail") String nicknameOrEmail);
    // ...
}

// En Implementación:
@WebService(
    serviceName = "UserService", 
    portName = "UserPort", 
    targetNamespace = "http://ws.turismouyapp/schema", 
    endpointInterface = "turismouyapp.webservices.interfaces.IUserWebService"
)
@XmlSeeAlso({DtTourist.class, DtSupplier.class})  // Para serialización JAXB
public class UserWebService implements IUserWebService {
    @WebMethod(exclude = true)
    public void publicar() {
        endpoint = Endpoint.publish("http://localhost:8007/ws/user", this);
    }
    // ...
}
```

### Anotaciones JAXB (DTOs)

```java
@XmlRootElement(name = "DtUser")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DtUser", propOrder = { "nickname", "name", "email", ... })
@XmlSeeAlso({DtTourist.class, DtSupplier.class})
public abstract class DtUser {
    private String nickname;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthDate() { return birthDate; }
    // ...
}
```

### Excepciones SOAP

```java
@WebFault(
    name = "RepeatedUserEmailFault", 
    targetNamespace = "http://ws.turismouyapp/schema",
    faultBean = "turismouyapp.core.exceptions.fault.RepeatedUserEmailFault"
)
@SuppressWarnings("serial")
public class RepeatedUserEmailException extends Exception {
    private RepeatedUserEmailFault faultInfo;
    
    public RepeatedUserEmailException(String message) {
        super(message);
        this.faultInfo = new RepeatedUserEmailFault(message);
    }
    
    public RepeatedUserEmailFault getFaultInfo() {
        return faultInfo;
    }
}

// Fault Bean (serializable):
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepeatedUserEmailFault")
public class RepeatedUserEmailFault {
    private String message;
    // ...
}
```

---

## 🔥 Troubleshooting

### Error: "Connection refused: 127.0.0.1:8007"

**Síntoma**:
```
[ERROR] Failed to download WSDL: http://localhost:8007/ws/user?wsdl
[ERROR] Connection refused: 127.0.0.1:8007
```

**Causa**: Intentas compilar frontend sin publisher activo.

**Solución**:
```bash
# Terminal 1: Publicar
cd backend && mvn exec:java -Prun-publisher

# Espera a ver los endpoints (5-10 segundos)

# Terminal 2: Compilar
cd frontend && mvn clean package
```

---

### Error: "Address already in use: port 8007"

**Síntoma**:
```
java.net.BindException: Address already in use: 127.0.0.1:8007
```

**Causa**: Ya hay un publisher corriendo.

**Solución**:
```bash
# Ver qué está usando el puerto
lsof -i :8007          # Linux/macOS
netstat -ano | findstr :8007  # Windows

# Matar el proceso
kill -9 <PID>          # Linux/macOS
taskkill /PID <PID> /F # Windows

# O usar puerto diferente (cambiar Publisher.java y pom.xml frontend)
```

---

### Error: "No Persistence provider"

**Síntoma**:
```
Exception [EclipseLink-4002]: No Persistence provider for EntityManager
```

**Causa**: BD no está disponible cuando servicio intenta acceder.

**Solución**:
```bash
# Asegurar que Tomcat esté corriendo
cd server/apache-tomcat-11.0.11
./bin/startup.sh

# Y esperar logs de HSQLDB:
# [DB] HSQLDB iniciado por Tomcat en puerto 9001
```

---

### Error: "ClassNotFoundException: UserService"

**Síntoma**:
```
java.lang.ClassNotFoundException: turismouyapp.webservices.UserService
```

**Causa**: Stubs no fueron generados o no están en classpath.

**Solución**:
```bash
# 1. Verificar que stubs existen
ls frontend/target/generated-sources/wsimport/turismouyapp/webservices/

# Si no existen:
# 2. Verificar Publisher activo
curl http://localhost:8007/ws/user?wsdl

# 3. Recompilar frontend
cd frontend && mvn clean package

# 4. En Eclipse: Maven → Update Project
```

---

### Error: "wsimport: Unknown host"

**Síntoma**:
```
[ERROR] wsimport failure: Unknown host: localhost
```

**Causa**: Problema de networking o DNS.

**Solución**:
```bash
# Editar frontend/pom.xml y cambiar localhost por 127.0.0.1
<wsdlUrl>http://127.0.0.1:8007/ws/user?wsdl</wsdlUrl>

# O verificar que Publisher escucha en 127.0.0.1
# En Publisher.java:
endpoint = Endpoint.publish("http://127.0.0.1:8007/ws/user", this);
```

---

## 📚 Referencias

### JAX-WS 4.0 (Metro)
- [JAX-WS Tutorial](https://eclipse-ee4j.github.io/metro-jax-ws/)
- [Jakarta XML Web Services Specification](https://jakarta.ee/specifications/xml-web-services/)

### JAXB 4.0 (XML Binding)
- [JAXB Documentation](https://eclipse-ee4j.github.io/jaxb-ri/)
- [Jakarta XML Binding Specification](https://jakarta.ee/specifications/xml-binding/)

### HSQLDB
- [HSQLDB Official Documentation](http://hsqldb.org/doc/2.0/guide/)

### Maven Plugins
- [jaxws-maven-plugin](https://www.mojohaus.org/jaxws-maven-plugin/)
- [maven-javadoc-plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/)

---

**Documento complementario del README.md**  
Última actualización: Noviembre 2025
