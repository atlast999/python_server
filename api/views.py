from django.http import JsonResponse
from api.models import Attendee
from api.serializers import AttendeeSerializer
from rest_framework.decorators import api_view
# for face recognition
import sys
import face_recognition

@api_view(['GET', 'POST'])
def recognite(request):
    try: 
        minImage = face_recognition.load_image_file("min.jpg")
        minEncoded = face_recognition.face_encodings(minImage)[0]
        image = request.FILES['file']
        imageEncoded = face_recognition.face_encodings(face_recognition.load_image_file(image))[0]
        knownFace = [minEncoded]
        res = face_recognition.compare_faces(knownFace, imageEncoded)
        return JsonResponse({'res': f'{res[0]}'})
    except :
        print(sys.exc_info()[0])
        return JsonResponse({'error': f'{sys.exc_info()[0]}'})

@api_view(['GET', 'POST'])
def request(request):
    if request.method == 'GET':
        attendees = Attendee.objects.all()
        serializer = AttendeeSerializer(attendees, many=True)
        return JsonResponse(serializer.data, safe=False)
    elif request.method == 'POST':
        attendee = Attendee(name="Hoan", attendId= "123")
        attendee.save()
        serializer = AttendeeSerializer(attendee)
        return JsonResponse(serializer.data)