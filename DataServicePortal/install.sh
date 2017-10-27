#/bin/sh

npm update

ng version

ng build --dev

if [ -d '../../dataserviceportal-website' ]; then
    rm -rf ../../dataserviceportal-website/*
fi

if [ -d 'dist' ]; then
    mv dist/* ../../dataserviceportal-website/.
fi
