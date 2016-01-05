TEMPLATE = app
CONFIG +=  c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp

LIBS+=-lfltk


CONFIG(debug, debug|release) {
    DESTDIR = $$PWD/bin/debug
    OBJECTS_DIR = $$PWD/build/debug/.obj
    MOC_DIR = $$PWD/build/debug/.moc
    RCC_DIR = $$PWD/build/debug/.rcc
    UI_DIR = $$PWD/build/debug/.ui
} else {
    DESTDIR = $$PWD/bin/release
    OBJECTS_DIR = $$PWD/build/release/.obj
    MOC_DIR = $$PWD/build/release/.moc
    RCC_DIR = $$PWD/build/release/.rcc
    UI_DIR = $$PWD/build/release/.ui
}
