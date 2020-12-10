from django.db import models

# Create your models here.

class Attendee(models.Model):
    name = models.TextField(default='new')
    attendId = models.TextField(default='0')
    objects = models.Manager()
    
