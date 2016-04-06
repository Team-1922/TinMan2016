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

ip = sys.argv[1]
#ip='10.19.22.2'

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

def FindBestContour(countours, testContour, testContourHeight):
    if len(countours) == 0:
        return -1,-1
    print "Getting Best Contour"

    bestContour = -1
    bestMatchVal = 5000
    for i in range(0,len(contours)):
        #resize the test contour
        #_,_,_,height = cv2.boundingRect(contours[i])
        #ratio = (float(height) / float(testContourHeight))
        #resizedContour = testContour * ratio
        #print ratio
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
            x,y,w,h = cv2.boundingRect(contours[i])
            aspect_ratio = float(w)/h
            if aspect_ratio > 1:
                passedContours.append(contours[i])
    print "Done Getting Contours"
    return passedContours

def GetHullsFromContours(contours):
    hulls = []
    for i in range(0,len(contours)):
        hulls.append(cv2.conexHull(contours[i]))
    return hulls

def SendBestToNetworkTables(contour, matchVal, isBadContour):
    if isBadContour == -1:
        area = -1
        w = -1
        h = -1
        centerX = -1
        centerY = -1
    else:
        area = cv2.contourArea(contour)
        x,y,w,h = cv2.boundingRect(contour)
        centerX = x + w/2.0
        centerY = y + h/2.0
    sd.putNumber('width',w)
    sd.putNumber('height',h)
    sd.putNumber('centerX', centerX)
    sd.putNumber('centerY', centerY)
    sd.putNumber('area', area)
    sd.putNumber('matchVal', matchVal)

#Setup the Camera Settings
cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_BRIGHTNESS, 1)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640) 
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480) 

#Load the Test Image
#img1 = cv2.imread('C:/Users/kjmac/Documents/GitHub/TinMan2016/OpenCV/TestWindow.jpg',0)
img1 = cv2.imread('/home/pi/vision/TestWindow.jpg',0)

_,threshTest = cv2.threshold(img1,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
_,contours, hierarchy = cv2.findContours(threshTest,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
testContour = contours[0]
_,_,_,testContourHeight = cv2.boundingRect(testContour)

def nothing(x):
    pass
lower_green = np.array([52, 18, 85])
upper_green = np.array([105,255,219])

while(True):
    
    # Take each frame
    _, frame = cap.read()

    # Convert BGR to Greyscale
    #gry = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # Threshold the greyscale image
    #ret3,mask = cv2.threshold(gry,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)
    mask = cv2.inRange(hsv, lower_green, upper_green)
    

    # Bitwise-AND mask and original image
    res = cv2.bitwise_and(frame,frame, mask= mask)

    # find the contours
    _,contours, hierarchy = cv2.findContours(mask,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)

    #DrawContours(frame,contours)

    #filter the contours
    contours = FilterContours(contours, 100)
    if contours == -1:
        pass
    else:

        #DrawContours(frame, contours)
        # get the best contour
        bestContour,bestMatch = FindBestContour(contours, testContour, testContourHeight)
        #Should there be a minimum here?  We don't want to try to look for a window that doesn't exist, but what is that min value? 20?
        if bestMatch == -1 or bestMatch == 5000:
            #no contours found
            SendBestToNetworkTables(0,bestMatch,-1)
            pass
        else:
            # put the big\gest contour on the frame
            #cv2.drawContours(frame, [bestContour], 0, (0,255,0), 3)
            SendBestToNetworkTables(bestContour,bestMatch,0)


    #cv2.imshow('frame',frame)
    #cv2.imshow('res',res)
    #cv2.imshow('Base',img1)
    k = cv2.waitKey(5)# & 0xFF
    if k == 27:
        break



# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
