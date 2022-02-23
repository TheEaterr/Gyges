rmdir output /s /q
md output
md output\Gyges
md output\Gyges\assets
md output\Gyges\jre
copy ..\assets output\Gyges\assets
xcopy "C:\Users\tykap\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.2.8-hotspot" output\Gyges\jre /s /e
"C:\Program Files (x86)\Launch4j\launch4jc.exe" launch4jconfig.xml
"C:\Program Files (x86)\Launch4j\launch4jc.exe" launch4jconfigCmdLine.xml
cd output\Gyges
"C:\Program Files\7-Zip\7z.exe" a -tzip ..\Gyges.zip -r * -mx5
cd ..
rmdir Gyges /s /q