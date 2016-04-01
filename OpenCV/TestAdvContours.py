import cv2
import numpy as np

img1 = cv2.imread('C:/Users/kjmac/Documents/GitHub/TinMan2016/OpenCV/star.jpg')
img2 = cv2.imread('C:/Users/kjmac/Documents/GitHub/TinMan2016/OpenCV/star2.jpg')
hsv1 = cv2.cvtColor(img1, cv2.COLOR_BGR2HSV)
hsv2 = cv2.cvtColor(img2, cv2.COLOR_BGR2HSV)

lower = np.array([0, 0, 127])
upper = np.array([255, 25, 255])
thresh = cv2.inRange(hsv1, lower, upper)
thresh2 = cv2.inRange(hsv2, lower, upper)

cntImage, contours, hiearchy = cv2.findContours(thresh,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
testContour = contours[0]

cntImage, contours, hiearchy = cv2.findContours(thresh2,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
bestContour = -1
prevMatchVal = 1.0
for i in range(0,len(contours)):
    thisMatchVal = cv2.matchShapes(testContour, contours[i], 1, 0.0)
    print thisMatchVal
    if thisMatchVal < prevMatchVal:
        bestContour = contours[i]
        prevMatchVal = thisMatchVal


cv2.drawContours(img1, [testContour], 0, (0,255,0), 3)
cv2.drawContours(img2, [bestContour], 0, (0,255,0), 3)
cv2.imshow('Match To',img1)
cv2.imshow('Match File',img2)
print prevMatchVal
cv2.waitKey()
