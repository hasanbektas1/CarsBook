# CarsBook

![Error](https://github.com/hasanbektas1/CarsBook/blob/master/app/src/main/res/drawable/cars%20(1).png) 
![Error](https://github.com/hasanbektas1/CarsBook/blob/master/app/src/main/res/drawable/cars2%20(1).png)

Bu projede **SQLite**, **RecyclerView**, **OptionsMenu**,**Camera And Gallery Permissions** , kullanılmıştır.

Projeden kısaca bahsedecek olursak galeriden seçilen veya kameradan çekilen araba resimini isim, model ve yılını kayıt edip ekranda listeleyip daha sonra istenilen arabaya tıklanıp detaylarını görmek.


## Geliştirme Adımları 

Öncelikle activitlerimizin xml görünümlerini oluşturuyoruz.

 ### activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>

```
 ### activity_car.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".CarActivity">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="300dp"
        android:layout_height="190dp"
        android:layout_marginTop="20dp"
        android:onClick="selectImage"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/selectimage" />

    <EditText
        android:id="@+id/carNameText"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:ems="10"
        android:hint="Car Name"
        android:inputType="textPersonName"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imageView" />

    <EditText
        android:id="@+id/modelNameText"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:ems="10"
        android:hint="Model Name"
        android:inputType="textPersonName"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/carNameText" />

    <EditText
        android:id="@+id/yearText"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:ems="10"
        android:hint="Year "
        android:inputType="number"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/modelNameText" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:backgroundTint="#071FBC"
        android:onClick="saveButton"
        android:text="Save"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/yearText" />

    <Button
        android:id="@+id/gallery"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginStart="100dp"
        android:layout_marginTop="80dp"
        android:layout_marginEnd="10dp"
        android:background="@drawable/ic_image"
        android:backgroundTint="@color/teal_200"
        android:onClick="galleryButton"
        app:layout_constraintEnd_toStartOf="@+id/camera"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:ignore="OnClick" />

    <Button
        android:id="@+id/camera"
        android:onClick="cameraButton"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginStart="10dp"
        android:layout_marginTop="80dp"
        android:layout_marginEnd="100dp"
        android:background="@drawable/ic_camera"
        android:backgroundTint="@color/teal_200"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/gallery"
        app:layout_constraintTop_toTopOf="parent"
        tools:ignore="OnClick" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
Şimdi liste ekranından yani Mainactivity'den yeni kayıt yapılacak CarActivity ekranına geçiş için **menu** oluşturuyoruz.
> res > New > Android Resource File
ile açılan pencerede menu ismini yazıp altında Resource type kısmında menu seçiyoruz.
res dosyası altında menu klasörümüz oluşuyor ve içerisinde menümuzun xmlinde görünümü yaratıyoruz.

### car_menu.xml
```

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/carAdd"
        android:title="Add Car"
        android:icon="@drawable/ic_add"
        app:showAsAction="always|collapseActionView"
        >

    </item>

</menu>
```



Daha sonra MainActivity sınıfımızda ouşturulan menü'yü bağlama işlemi yapılmalı, bunun için iki adet fonksiyonu override ediyoruz.
- onCreateOptionsMenu
- onOptionsItemSelected
ilk fonksiyonda menümüzü MainAcitivitemiz ile bağlıyoruz ikincisinde ise menüdeki iteme tıklanınca ne olacağını yazıyoruz.

Listeye ekleme yapabilmek için Galeriden seçilen veya Cameradan çekilen fotoğrafı alabilmek için izinler gereklidir

Projemizde AndroidManifest.xml içerisinde gerekli olan izinleri eklemeliyiz.

- android.permission.READ_EXTERNAL_STORAGE"
- android.permission.CAMERA"
- android.permission.WRITE_EXTERNAL_STORAGE"
 
 İzinleri ekledikten sonra iznin protection leveli **dangerous** ise kullanıcıdan uygulama içeriside de izin almamız gerekiyor.
 
 Kullanıcı galeriye gitmeden önce galleryButton fonksiyonu içerisinde iznimizi istiyoruz.
 
 İzin geri dönüşü için ve izin sonucunda gidilen galeriden veri dönüşü için  **ActivityResultLauncher** sınıfını kullanıyoruz.
```
private lateinit var intentResultLauncher : ActivityResultLauncher<Intent>
private lateinit var permissionLaunchergallery : ActivityResultLauncher<String>
```
 
 
 






