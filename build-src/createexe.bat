rmdir output\Gyges /s /q
md output\Gyges
md output\Gyges\assets
copy ..\assets output\Gyges\assets
"C:\Program Files (x86)\Launch4j\launch4jc.exe" launch4jconfig.xml