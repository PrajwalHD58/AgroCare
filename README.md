# AgroCare
An android application for smart monitoring of agriculture.

## Authors

- [@PrajwalHD58](https://github.com/PrajwalHD58)

## Features
‘Agrocare’ is an android app that especially focuses on four ways of smart farming. 

**1)Crop growth event Monitor:** Groeth monitoring for crop events like watering crop, phase change, Fertilizers given,presticides given etc;
  #### Functionality used:
  1) MVVM architecture(Model-View-View-Model)
  2) Sqlite for local storage.
  3) Drawable layout.
  4) ListView
  5) CardView
  6) TabHost

**2)Machine learning in detecting and classifying diseases of a plant leaf:** Pretrained Machine learning TensorFlow lite model used in the project for detection of plant diseases based on image capturing of plant leaf's detecting diseases from the trained model. We use an interpreter & classifier to integrate the ml model without an app to get desired outcomes. This process is very fast, accurate, convenient & easy to use.
  #### Functionality used:
  1) Pretrained machine learning model(tflite file)
  2) ML-kit 

**3)Precise area monitors and measurement using google Maps API:** Generally, in our farming tradition, we measure areas just by guessing this may cause improper distribution of nutrition, fertilizers, insecticides etc. Using google map API we can quickly measure & monitor our required area.
  #### Functionality used:
  1) Google map api
  2) Area Calculation using computeArea function

**4)Weather monitoring using open weather API:** Whether is a very important aspect of farming. Farmers need to monitor weather data to perform their incoming steps or work. We use open weather API to fetch data of weather data like Temperature, wind speed, precipitation, humidity, rain measures etc. We use a standard HTTP request mechanism to fetch data from Api & display it in our app.
  #### Functionality used:
  1) Open Weather API
  2) Http request for json parsing.

## ScreenShots
![Team Illuminati](https://user-images.githubusercontent.com/65499831/172834593-ffe0f743-dc5e-49ab-89c0-831e3b2e0d1f.png)
