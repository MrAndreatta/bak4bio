class Content < ActiveRecord::Base
  has_many :blasts, :foreign_key => :entry_id
  belongs_to :owner, :class_name => "User", :foreign_key => :owner_id
  
  paginates_per 10
  
  attr_accessible :source, :source_file_name, :source_content_type, 
                  :source_file_size, :source_updated_at,
                  :description, :owner_id
                  
  has_attached_file :source,
                    :url => "/:current_user/:basename.:extension",
                    :path => "#{Rails.root}/public:url" 
                    
                    
  validates_presence_of :description, :owner_id, :source                  
  validates_uniqueness_of :source_file_name, :if => :has_source 
  validates_uniqueness_of :description
  
  before_destroy :can_remove?
  
  private
  def has_source
    !self.source.blank?
  end
  
  def can_remove?
    unless self.blasts.blank?
      errors.add(:base, "Can't remove content with blast operations")
      return false
    end
  end
                                        
  # interpolate in paperclip
  Paperclip.interpolates :current_user  do |attachment, style|
    attachment.instance.owner_id
  end
 
end
