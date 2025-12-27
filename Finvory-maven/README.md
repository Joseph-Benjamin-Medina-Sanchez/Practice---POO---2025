# Finvory

Aplicación de escritorio en Java Swing para gestión de inventario, facturación y control de clientes/proveedores. Incluye un splash screen personalizado, persistencia local con archivos JSON/CSV y sincronización opcional con MongoDB Atlas.

## Requisitos
- Java 17
- Maven 3.9+
- (Opcional) Acceso a una base MongoDB Atlas para sincronizar productos, clientes, proveedores e invoices.

## Configuración rápida
1. Clona el repositorio y ubícate en la raíz del proyecto (`Finvory-maven`).
2. Define la variable de entorno `MONGODB_URI` si quieres habilitar la sincronización en la nube. Ejemplo:
   ```bash
   export MONGODB_URI="mongodb+srv://<usuario>:<password>@<cluster>/finvory?retryWrites=true&w=majority"
   ```
3. Los datos locales se almacenan en `data/<usuario_empresa>/` y se apoyan en `utils/users.json` para las credenciales. Mantén estos archivos accesibles cuando empaquetes o despliegues.

## Construcción y ejecución
```bash
mvn clean package
java -jar target/Finvory-maven-1.0.jar
```
El empaquetado usa `maven-shade-plugin` para generar un JAR ejecutable con todas las dependencias.

## Consejos de desarrollo
- La clase `FinvoryApp` es el punto de entrada y configura el `MongoDBConnection`, el `Database` local y la UI de splash.
- `FinvoryController` coordina la carga/guardado de datos (`Database`), el login y la navegación entre vistas.
- `FinvoryData` agrupa inventarios, productos, clientes y parámetros financieros (impuestos, descuentos) y sirve como modelo central para cálculos.
- Para diagnosticar problemas de sincronización, revisa los mensajes de consola sobre descarga de datos desde MongoDB y el contenido de `data/<usuario_empresa>/`.
