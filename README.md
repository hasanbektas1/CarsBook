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
> 
ile açılan pencerede menu ismini yazıp altında Resource type kısmında menu seçiyoruz.
res dosyası altında menu klasörümüz oluşuyor ve içerisinde menümuzun xmlinde görünümü yaratıyoruz.
menüdeki icon görünümü için

> res > drawable > Vector Asset 
> 
seçip açılan pencerede oluşturabiliyoruz sonra menu xmli içerisinde oluşturulan iconu seçiyoruz.


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

Listeye ekleme yapabilmek için galeriden seçilen veya Kameradan çekilen fotoğrafı alabilmek için izinler gereklidir

Projemizde AndroidManifest.xml içerisinde gerekli olan izinleri eklemeliyiz.

- android.permission.READ_EXTERNAL_STORAGE"
- android.permission.CAMERA"
- android.permission.WRITE_EXTERNAL_STORAGE"
 
 İzinleri ekledikten sonra iznin protection leveli **dangerous** ise kullanıcıdan uygulama içeriside de izin almamız gerekiyor.
 
 
 İzin geri dönüşü için ve izin sonucunda gidilen galeriden veri dönüşü için  **ActivityResultLauncher** sınıfını kullanıyoruz.
```
private lateinit var intentResultLauncher : ActivityResultLauncher<Intent>
private lateinit var permissionLaunchergallery : ActivityResultLauncher<String>
```
ActivityResultLauncher <> işaretleri içerisinde bir tip ister ve hangi tipte olacağını oraya giriyoruz
İzinler String olduğu için String olarak belirtiyouz

izin verilmesi durumunda galeriye gidip oradaki fotoğrafın adresini yani url'sini buluyoruz

Daha sonra bir bitmap oluşturmalıyız fotoğrafı SQLiteye kayıt ederken fotoğrafın 1 MB' yi geçmesini önlemek için, aksi takdirde programımız düzgün çalışmayabilir.
Url'yi bitmap olarak dönüştürmek için ise **ImageDecoder** sınıfını kullanıyoruz. Ancak eski versiyonlarda çalışmadığı için önce versiyon kontorolü yapıyoruz.
Ve son olarak fotoğrafı ekranımızda gösteriyoruz.
```
        intentResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK){
                    val intentFromResult = it.data
                    if (intentFromResult != null){
                         val imageData = intentFromResult.data
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(contentResolver,imageData!!)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            }else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            })
```

Daha sonra istenilecek izinler için Launcher'larımızı oluşturuyoruz.

 **Galeri İzni**
```
        permissionLaunchergallery = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {
                if(it){
                    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intentResultLauncher.launch(intentToGallery)
                }else{
                    Toast.makeText(this@CarActivity,"Permission Needed",Toast.LENGTH_LONG).show()
                }
            })
```
 **Kamera İzni**
 ```
     permissionLaunchercamera = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->

            if (result ){
                binding.imageView.visibility = View.VISIBLE
                binding.gallery.visibility = View.INVISIBLE
                binding.camera.visibility = View.INVISIBLE

                openCamera()

            }else{
                Toast.makeText(applicationContext,"Permission needed!", Toast.LENGTH_LONG).show()
            }
        }
```

ve izin istenilecek yerlerde bu oluşturulan Launcher'imizi çağırıyoruz.
```
  fun galleryButton(view : View){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionLaunchergallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }else {
            binding.imageView.visibility = View.VISIBLE
            binding.gallery.visibility = View.INVISIBLE
            binding.camera.visibility = View.INVISIBLE

            val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intentResultLauncher.launch(intentToGallery)
        }
    }
```

```
   fun cameraButton(view : View){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            permissionLaunchercamera.launch(android.Manifest.permission.CAMERA)
        }else{
            openCamera()
            binding.imageView.visibility = View.VISIBLE
            binding.gallery.visibility = View.INVISIBLE
            binding.camera.visibility = View.INVISIBLE
        }
    }
```

kamera izni verilmesi durumunda kamerayı açma fonksiyonuzumu yazıyoruz.
```
   private fun openCamera() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE,"imageTitle")
        imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }
```

Kameradan çekilen fotoğrafı alabilmek için onActivityResult fonksiyonumuzu override ediyoruz ve galeriden seçilen fotoğraf gibi SQLiteye kayıt etmeden küçültme yapabilmek için bitmap'a dünüştürüyoruz.
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if (imageUri != null){
                try {
                    if (Build.VERSION.SDK_INT >=28){

                        val source = ImageDecoder.createSource(this@CarActivity.contentResolver,imageUri!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        binding.imageView.setImageBitmap(selectedBitmap)
                        println(selectedBitmap)

                    }else{
                        selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUri)
                        binding.imageView.setImageBitmap(selectedBitmap)
                    }

                } catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
            }
        }
    }
```

SQLiteye görsellirimizi kaydetmek için bitmap olarak dönüştürülen görsellerimizi küçültme fonksiyonumuzu yazıyoruz.
Görseli bozmadan yüksekliğini ve genişliğini aynı oranda küçültme yapıyoruz.
```
    private fun makeSmallBitmap(image:Bitmap,maximumSize : Int): Bitmap {

        var width = image.width
        var height = image.height

        var bitmapRatio : Double = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1){
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        }else{
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
    }
 ```
 
 Artık kayıt etme butonunun kod bloğu içerisini yazma aşamasına geldik.
 
 Öncelikle SQLiteye kayıt edilecek olan carName, modelName ve year verilerini degişkenlere aktardık daha sonra SQLİteye kayıt işlemlerini yapacağız ama görseli bitmap olarak SQLiteye kayıt edemeyiz. Görselimizi **ByteArray** olarak dönüştürmeliyiz.
Bunun için **ByteArrayOutputStream** yardımcı sınıfını kullanıyoruz. 
```
 fun saveButton(view : View){
        val carName = binding.carNameText.text.toString()
        val modelName = binding.modelNameText.text.toString()
        val year = binding.yearText.text.toString()

        if (selectedBitmap !=null){
            val smallBitmap = makeSmallBitmap(selectedBitmap!!,300)

            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()
 ```
 
 Yukarıdaki kod bloğunda görüldüğü üzere görselimizi SQLiteye kayıt edilecek şekilde bir veriye dönüştürdük.
 Şimdi SQLiteye kayıt işlemi için try and catch içerisinde kodlarımızı yazıyoruz.
 ```
  try {
                val database = this.openOrCreateDatabase("Cars", MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS cars(id INTEGER PRIMARY KEY, carname VARCHAR, modelname VARCHAR, year VARCHAR, image BLOB)")
                val sqlString = "INSERT INTO cars(carname, modelname, year, image) VALUES(?,?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,carName)
                statement.bindString(2,modelName)
                statement.bindString(3,year)
                statement.bindBlob(4,byteArray)
                statement.execute()
 ```
 Yukarıdaki kod bloğunda öncelikle SQLite databasemizi oluşturduktan sonra bu database ismimizi belirliyoruz. 
 Daha sonra SQLite kodlarımıza bir tablo yok ise oluştur komutunun ardından tablodaki sütunları ve tiplerini belirliyoruz.
 verilen id değeri veri eklendikçe otomatik olarak artacaktır ve verileri çekmek için kullanacağız.
 ```
 val sqlString = "INSERT INTO cars(carname, modelname, year, image) VALUES(?,?,?,?)"
```
Bu kısımda ise kullanıcıdan gelen verilerimiz bir degişkende oldugu için soru işaretleri ile birbirine bağlama işlemlerini yapacağız. Bu bağlama işlemi için **compileStatement()** metodu ile hangi türde verinin hangi soru işareti ile bağlanması gerektiğini yazıyoruz. Artık kayıt işlemini tamamlamış olduk

Şimdi ise kayıt işlemi bittikten sonra giriş ekranına yani MainActivity'ye dönüyoruz.
```
val intent = Intent(this@CarActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
```
İntent ile recyclerView liste ekranına dönüş yaparken arkada açık kalan activity olamaması için **addFlags()** kullanıyoruz ve önceki açık olan activityleri kapatıyoruz.

RecyclerView liste ekranına dönüş yaptıktan sonra listede gösterilecek isim ve görseli SQLite veri tabanından çekme işlemini yapacağız. Direkt liste ekranında gözükmesi için kodlarımızı **onCreate()** fonksiyonu içerisinde yazıyoruz.
```
    try {
            val database = this.openOrCreateDatabase("Cars", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM cars",null)
            val carNameIx = cursor.getColumnIndex("carname")
            val idIx = cursor.getColumnIndex("id")
            val imageIx = cursor.getColumnIndex("image")
```
İlk olarak tekrar database'mizi aynı isimde olacak şekilde oluşturduktan sonra rawQuery diyip *Cars* tablosundaki herşeyi çek diyoruz. Bir sonraki adımda ise sütun isimlerini int türünde degişkenlere atayarak daha sonrada kullanılabilir hale getirdik.
Verileri çekmek için ise while() döngüsünü kullanacağız.
```
  while (cursor.moveToNext()){

                val name = cursor.getString(carNameIx)
                val id = cursor.getInt(idIx)

                val byteArray = cursor.getBlob(imageIx)
                val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                val car = Car(name,id,image)
                carList.add(car)
            }
        cursor.close()

```
while döngüsü içersinde **moveToNext()** methodu yardımı ile cursor tabloda ilerleyebildiği kadar ilerlesin ve bu ilerleme sırasında her defasında verileri değişkenlere aktarsın. Bu değişkenleri model sınıfımız yani *Car* sınıfı yardımı ile recyclerView ekranında kolayca gösterebilmek için bir ArrayList oluşturup içerisine ekliyoruz. 
Bu adımdaki son işlem olarak  *cursor.close()* diyip imlecimizi kapattık.

Şimdi RecyclerView için görünümünü ve adapterini oluşturalım. Öncelikle recyclerView ekranında her bir row'da neler görüneceğini belirlemek için bir xml görünümü oluşturuyoruz.

> res > layout > New > Layout Resource File

ile açılan pencerede xml ismimizi belirledikten sonra Root element kısmında oluşturulan xml'in hangi layoutla başlaması gerektiğini belirleyebiliriz.

 ***recycler_row.xml***
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <ImageView
            android:id="@+id/recyclerImgView"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center_vertical"
            android:layout_margin="8dp"
            android:scaleType="centerCrop" />

        <TextView
            android:id="@+id/recyclerTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Test"
            android:textStyle="bold"
            android:textSize="24sp"
            android:layout_margin="8dp"
            android:textColor="@color/black"/>
    </LinearLayout>

</LinearLayout>
```
Listemiz yukarıdan aşağıya doğru sıralanması için LinearLayout ile başlatıp içeriden *orientation* türünü *vertical* yaptık
Şimdi de recyclerView adapterimizi oluşturalım. Öncelikle paket ismimize sağ tıklayıp normal bir class yaratıyoruz.
Daha sonra bu sınıfımızın bir RecyclerView adapteri olucağını belirtince bizden bir ViewHolder adında bir yardımcı sınıf oluşturmamızı ister, bu sınıf görünüm tutucudur yani xmli bağlama her bir rowda gösterilecekleri belirleme gibi işlemleri koordine eden bir yardımcı sınıf. içerisinde bir işlem yapılmasa da böyle bir sınıf oluşturmamızı ister.
```
class CarAdapter(val carList: ArrayList<Car>) : RecyclerView.Adapter<CarAdapter.CarHolder>() {

    class CarHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarHolder(binding)
    }
    override fun onBindViewHolder(holder: CarHolder, position: Int) {

        holder.binding.recyclerTextView.text = carList.get(position).name
        holder.binding.recyclerImgView.setImageBitmap(carList.get(position).image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,CarActivity::class.java)
            intent.putExtra("info","detail")
            intent.putExtra("id",carList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return carList.size
    }
}
```
Yukarıda görüldüğü üzere RecyclerView.Adapter'dan extend ettiğimiz için 3 adet override edilecek fonksiyon verir.

- onCreateViewHolder
- onBindViewHolder
- getItemCount

*onCreateViewHolder()* fonksiyonumuzda layout ile baglama işlemini yapacağız, *onBindViewHolder()* fonksiyonunda ise bağlandıktan sonra ne olacağını yanı hangi componentte hangi veriyi göstereceğimizi veya bu gösterilenlere tıklanınca ne olacağını yazıyoruz. Son olarak getItemCount() fonksiyonunda ise kaç adet row olacak, tüm listenin uzunluğu diyerek listemin hepsini verebiliyorum.





