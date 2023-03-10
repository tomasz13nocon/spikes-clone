@echo off
setlocal enabledelayedexpansion
set x=1
for %%i in (*.png) do (
	for /f "delims=_. tokens=2" %%n in ("%%i") do (
		set number=00000%%n
		set file[!number:~-6!]=%%i
	)
)
for /f "tokens=2 delims==" %%i in ('set file[') do (
	ren %%i keyframe_!x!.png
	set /a x+=1
)