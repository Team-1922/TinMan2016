import random
import cv2
import numpy as np


#begin network tables
import sys
import time
from networktables import NetworkTable

# To see messages from networktables, you must setup logging
#import logging
#logging.basicConfig(level=logging.DEBUG)

#if len(sys.argv) != 2:
#    print("Error: specify an IP to connect to!")
#    exit(0)

#ip = sys.argv[1]
ip='127.0.0.1'

NetworkTable.setIPAddress(ip)
NetworkTable.setClientMode()
NetworkTable.initialize()

class ConnectionListener:

    def connected(self, table):
        print("Connected to", table.getRemoteAddress(), table)

    def disconnected(self, table):
        print("Disconnected", table)

c_listener = ConnectionListener()

sd = NetworkTable.getTable("GRIP/myContoursReport")
sd.addConnectionListener(c_listener)

#END NETWORKTABLES

def FindBestContour(countours, testContour):
    if len(countours) == 0:
        return -1,-1
    print "Getting Best Contour"

    bestContour = -1
    bestMatchVal = 5000
    for i in range(0,len(contours)):
        thisMatchVal = cv2.matchShapes(testContour, contours[i], 1, 0.0)
        print thisMatchVal
        if thisMatchVal < bestMatchVal:
            bestContour = contours[i]
            bestMatchVal = thisMatchVal

    print "Done Getting Best Contour"
    return bestContour,bestMatchVal

def DrawContours(image, contours):
    for i in range(0, len(contours)):
        scale = cv2.contourArea(contours[i])
        cv2.drawContours(image, contours, i, (scale, scale, scale), 2)

def FilterContours(contours, minArea):
    if len(contours) == 0:
        return -1

    print "Getting Bigger Contours"
    passedContours = []
    for i in range(0,len(contours)):
        area = cv2.contourArea(contours[i])
        print area
        if area >= minArea:
            #x,y,w,h = cv2.boundingRect(contours[i])
            #aspect_ratio = float(w)/h
            #if aspect_ratio > 1:
            passedContours.append(contours[i])
    print "Done Getting Contours"
    return passedContours

def GetHullsFromContours(contours):
    hulls = []
    for i in range(0,len(contours)):
        hulls.append(cv2.conexHull(contours[i]))
    return hulls

def SendBestToNetworkTables(contour):
    area = cv2.contourArea(contour)
    x,y,w,h = cv2.boundingRect(contour)
    centerX = x + w/2.0
    centerY = y + h/2.0
    sd.putNumber('width',w)
    sd.putNumber('height',h)
    sd.putNumber('centerX', centerX)
    sd.putNumber('centerY', centerY)
    sd.putNumber('area', area)
    pass

cap = cv2.VideoCapture(1)
img1 = cv2.imread('C:/Users/kjmac/Documents/GitHub/TinMan2016/OpenCV/TestWindow.jpg')
frame = cv2.imread('C:/Users/kjmac/Pictures/RealFullField/3.jpg')

# define range of green color in HSV (backup)
lower_green = np.array([69,26,200])
upper_green = np.array([90,95,255])

hsvTest = cv2.cvtColor(img1, cv2.COLOR_BGR2HSV)
threshTest = cv2.inRange(hsvTest, np.array([0,0,127]), np.array([255,50,255]))
_,contours, hierarchy = cv2.findContours(threshTest,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
testContour = contours[0]

def nothing(x):
    pass

cv2.namedWindow('image')
cv2.createTrackbar('H Min','image',69,255,nothing)
cv2.createTrackbar('H Max','image',105,255,nothing)
cv2.createTrackbar('S Min','image',93,255,nothing)
cv2.createTrackbar('S Max','image',255,255,nothing)
cv2.createTrackbar('V Min','image',85,255,nothing)
cv2.createTrackbar('V Max','image',219,255,nothing)

while(True):

    hm = cv2.getTrackbarPos('H Min','image')
    sm = cv2.getTrackbarPos('S Min','image')
    vm = cv2.getTrackbarPos('V Min','image')

    hM = cv2.getTrackbarPos('H Max','image')
    sM = cv2.getTrackbarPos('S Max','image')
    vM = cv2.getTrackbarPos('V Max','image')

    lower_green = np.array([hm, sm, vm])
    upper_green = np.array([hM, sM, vM])

    # Take each frame
    #_, frame = cap.read()
    frame = cv2.imread('C:/Users/kjmac/Pictures/RealFullField/3.jpg')

    # Convert BGR to HSV
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # Threshold the HSV image to get only green colors
    mask = cv2.inRange(hsv, lower_green, upper_green)

    # Bitwise-AND mask and original image
    res = cv2.bitwise_and(frame,frame, mask= mask)

    # find the contours
    _,contours, hierarchy = cv2.findContours(mask,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)

    #NEW STUFF HERE

    #edges = cv2.Canny(frame, 255, 255)

    #_, contours, hierarchy = cv2.findContours(edges,cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)


    #DrawContours(frame, contours)

    #contours = FilterContours(contours, 100)
    #if contours == -1:
    #    pass
    #else:

        # get the best contour
    #    bestContour,bestMatch = FindBestContour(contours, testContour)
    #    if bestMatch == -1:
            #no contours found
    #        pass
    #    else:
            # put the biggest contour on the frame
    #        cv2.drawContours(frame, [bestContour], 0, (0,0,255), 3)
            #pass


    #cv2.imshow('Edges', edges)
    #END NEW STUFF
    #DrawContours(frame,contours)

    #filter the contours
    contours = FilterContours(contours, 100)
    if contours == -1:
        pass
    else:

        DrawContours(frame, contours)
        # get the best contour
        bestContour,bestMatch = FindBestContour(contours, testContour)
        if bestMatch == -1:# or bestMatch > 5:
            #no contours found
            pass
        else:
            # put the biggest contour on the frame
            cv2.drawContours(frame, [bestContour], 0, (0,255,0), 3)
            SendBestToNetworkTables(bestContour)


    cv2.imshow('frame',frame)
    cv2.imshow('res',res)
    cv2.imshow('Base',img1)
    k = cv2.waitKey(5)# & 0xFF
    if k == 27:
        break



# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
