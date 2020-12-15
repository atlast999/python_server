from django.db import models

# Create your models here.

class Attendee(models.Model):
    name = models.TextField(default='name')
    attendId = models.TextField(default='0')
    image = models.TextField(default='placeholder.jpg')
    objects = models.Manager()
    
