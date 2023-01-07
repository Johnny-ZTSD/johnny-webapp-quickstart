set REPOSITORY_PATH=D:\Program-Data\Maven-Repository

@rem 临时修改当前控制台窗口的代码页(字符集) | 936(GBK) / 65001(UTF-8)
chcp 65001

rem 正在搜索...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*lastUpdated*"') do (
    del /s /q %%i
)
rem 搜索完毕
pause*
