# CarsBook

![Error](https://github.com/hasanbektas1/CarsBook/blob/master/app/src/main/res/drawable/cars%20(1).png) 
![Error](https://github.com/hasanbektas1/CarsBook/blob/master/app/src/main/res/drawable/cars2%20(1).png)

Bu projede **SQLite**, **RecyclerView**, **OptionsMenu**,**Camera And Gallery Permissions** , kullanılmıştır.

Projeden kısaca bahsedecek olursak galeriden seçilen veya kameradan çekilen araba resimini isim, model ve yılını kayıt edip ekranda listeleyip daha sonra istenilen arabaya tıklanıp detaylarını görmek.


## Geliştirme Adımları 

Öncelikle activitlerimizin xml görünümlerini oluşturuyoruz.

MainAcitivity.xml


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
