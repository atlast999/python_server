from django.http import JsonResponse
from api.models import Attendee
from api.serializers import AttendeeSerializer
from rest_framework.decorators import api_view
import json
from django.core.files.storage import default_storage
# for face recognition
import sys
import face_recognition
import os
from data import facedata

@api_view(['POST'])
def recognite(request):
    try: 
        image = request.FILES['file']
        res = facedata.isFaceRecognited(image)
        if res == -2:
            return JsonResponse({'error': f'check log for more'})
        if res == -1:
            return JsonResponse({'res': f'{res}'})
        serializer = AttendeeSerializer(res)
        return JsonResponse(serializer.data, safe=False)
    except :
        print(sys.exc_info()[0])
        return JsonResponse({'error': f'{sys.exc_info()[0]}'})

@api_view(['POST'])
def delete(request):
        try:
            att = Attendee.objects.filter(attendId = request.data['id'])
            filePath = os.path.join('images', att[0].image)
            default_storage.delete(filePath)
            att.delete()
            attendees = Attendee.objects.all()
            serializer = AttendeeSerializer(attendees, many = True)
            return JsonResponse(serializer.data, safe = False)
        except:
            return JsonResponse({'error': f'{sys.exc_info()[0]}'})

@api_view(['POST'])
def upload(request):
    try:
        attendeeName = request.data['name']
        attendeeId = request.data['id']
        uploadedFile = request.FILES['file']
        savedName = facedata.saveData(attendeeName, attendeeId, uploadedFile)
        return JsonResponse({'code': 'add new person: {}'.format(savedName)})
    except:
        print('err: ', sys.exc_info()[0])
        return JsonResponse({'code': 'error when handle uploaded photo nah'})    

@api_view(['GET'])
def allUsers(request):
    try:
        attendees = Attendee.objects.all()
        serializer = AttendeeSerializer(attendees, many = True)
        return JsonResponse(serializer.data, safe = False)
    except:
        print('err: ', sys.exc_info()[0])
        return JsonResponse({'code': 'error when get all users'})    