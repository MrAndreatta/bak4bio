class User < ActiveRecord::Base
  has_many :contents
  has_many :blasts
  
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable, 
         :token_authenticatable, :confirmable, :timeoutable

  attr_accessible :email, :name, :password, :password_confirmation, 
                  :remember_me, :unconfirmed_email,
                  :content_ids, :blast_ids
  
  def self.current_user
    Thread.current[:current_user]
  end

  def self.current_user=(user)
    Thread.current[:current_user] = user
  end
  
end
