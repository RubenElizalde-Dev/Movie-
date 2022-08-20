# Movies!

Una aplicación que te permitirá ver las reseña de tus películas favoritas, así como también te mostrara las películas más queridas por el mundo, las películas en tendencia y las más populares en el momento.

![ezgif-4-86035203c7](https://user-images.githubusercontent.com/70008618/185739284-de16c391-0902-4492-84b7-3c21e08e0532.gif)

## Descarga
Ve a la carpeta [apk](https://github.com/RubenElizalde-Dev/MoviesApp/tree/main/apk) y descarga el archivo ***app-debug.apk*** e instálalo en tu celular Android.

## Tech stack
- Nivel mínimo SDK 21
- Basado en [Kotlin](https://kotlinlang.org/) + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) para asíncrono.

- Jetpack
    - Lifecycle - Observar los ciclos de vida de Android y manejar los 
      estados de la interfaz de usuario en los cambios del ciclo de 
      vida.
    - ViewModel - Gestiona el titular de los datos relacionados con la 
      interfaz de usuario y el ciclo de vida permite a los
      datos sobrevivir a los cambios de configuración, como las 
      rotaciones 
      de pantalla.
    - DataBinding - Vincula los componentes de la interfaz de usuario en 
      sus 
      diseños a las fuentes de datos en su aplicación mediante un 
      formato 
      declarativo en lugar de mediante programación.
    - SharedPreferences - Construye la base de datos 
      proporcionando una abstracción
      de tipo map para permitir un acceso fluido a la base de datos.

- Arquitectura

  - Arquitectura MVVM (View - DataBinding - ViewModel - Model)
  - Bindables - Kit de enlace de datos de Android para notificar cambios de datos a las capas de la interfaz de usuario.

- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construcción de REST APIs.

- [Material-Components](https://github.com/material-components/material-components-android) - Componentes de Material design para crear animaciones y CardView.

- [Secrets Gradle](https://developers.google.com/maps/documentation/android-sdk/secrets-gradle-plugin) - Kit de seguridad para ocultar datos sensibles de la aplicacion como lo sería una **API_KEY**

- Vistas personalizadas
   - [Carousel](https://github.com/sparrow007/CarouselRecyclerview) - Carousel personalizado en 3D
   - [Picasso](https://github.com/square/picasso) - Urls a ImageView
   - [Lottie](https://github.com/airbnb/lottie-android) - Animaciones 

## Arquitectura
Movies!, está basado en la arquitectura MVVM utilizando ideas de Clean architecture.

![500px-MVVMPattern](https://user-images.githubusercontent.com/70008618/185739962-87e177c3-f6c6-4a98-a605-bb5249c2b215.png)

## Open API
Movies!, utiliza [TheMovieDB API](https://developers.themoviedb.org/3/getting-started/introduction).

[TheMovieDB API](https://developers.themoviedb.org/3/getting-started/introduction) provee una interfaz RESTful API que nos da el acceso a objetos altamente detallados para poder construir miles de líneas relacionadas a peliculas.

![gVZIvphd_400x400](https://user-images.githubusercontent.com/70008618/185739976-724cb851-fabf-4ff4-a4c7-592c59f7337c.jpg)

# License
```
Designed and developed by 2022 Ruben Manuel Elizalde Salzar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
