import sys
import os
import face_recognition
from django.core.files.storage import default_storage

from api.models import Attendee

allAttendees = []
encodedImages = []
needUpdate = False

def updateListEncodedFace():
    try: 
        print('updating list')
        global needUpdate
        global allAttendees
        global encodedImages
        needUpdate = False
        allAttendees = []
        allAttendees = Attendee.objects.all()
        encodedImages = []
        for attendee in allAttendees:
            image = face_recognition.load_image_file(os.path.join('images', attendee.image))
            faceEncoded = face_recognition.face_encodings(image)
            encodedImages.append(faceEncoded[0])
    except:
        print('err at update list: ', sys.exc_info()[0])

def isFaceRecognited(file):
    try:
        global allAttendees
        global encodedImages
        if len(encodedImages) == 0 or needUpdate == True:
            updateListEncodedFace()
        image = face_recognition.load_image_file(file)
        imageEncoded = face_recognition.face_encodings(image)[0]
        for index in range(len(encodedImages)):
            faceEncoded = encodedImages[index]
            distance = face_recognition.face_distance([faceEncoded], imageEncoded)
            print('distance: ', distance, 'to {}'.format(allAttendees[index].name))
            if distance[0] < 0.45:
                return allAttendees[index]
            
        return -1
    except:
        print('err at recognite: ', sys.exc_info()[0])
        return(-2)

def saveData(name, id, file):
    try:
        global needUpdate
        attendee = Attendee(name= name, attendId= id, image=file.name)
        attendee.save()
        savePath = os.path.join('images', file.name)
        path = default_storage.save(savePath, file)
        needUpdate = True
        return attendee.name
    except:
        print('err: ', sys.exc_info()[0])
    