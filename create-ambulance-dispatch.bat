@echo off
setlocal

REM Change this to your desired project location
set "ROOT=%cd%\ambulance-dispatch-system"

REM Create folders
mkdir "%ROOT%\src\main\java\com\example\ambulancedispatch\model"
mkdir "%ROOT%\src\main\java\com\example\ambulancedispatch\repository"
mkdir "%ROOT%\src\main\java\com\example\ambulancedispatch\service"
mkdir "%ROOT%\src\main\java\com\example\ambulancedispatch\controller"
mkdir "%ROOT%\src\main\resources\templates"

REM Create files
type nul > "%ROOT%\pom.xml"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\AmbulanceDispatchApplication.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\model\Ambulance.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\model\Hospital.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\model\Patient.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\model\Request.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\model\DispatchHistory.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\repository\AmbulanceRepository.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\repository\HospitalRepository.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\repository\PatientRepository.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\repository\RequestRepository.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\repository\DispatchHistoryRepository.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\service\DispatcherService.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\service\AmbulanceService.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\service\PatientService.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\controller\AmbulanceController.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\controller\PatientController.java"
type nul > "%ROOT%\src\main\java\com\example\ambulancedispatch\controller\DispatchController.java"
type nul > "%ROOT%\src\main\resources\application.properties"
type nul > "%ROOT%\src\main\resources\templates\home.html"
type nul > "%ROOT%\README.md"

echo Project structure created at: %ROOT%
pause