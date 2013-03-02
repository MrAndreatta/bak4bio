# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

# Cria o administrador do sistema
admin_user = User.create({:name => 'Administrator', :email => "bak4bio@gmail.com", :password => "%=BAK4BIO=%"})
Administrator.create({:user_id =>  admin_user.id})
