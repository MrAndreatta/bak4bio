class Content < ActiveRecord::Base
  has_one :blast
  belongs_to :owner, :class_name => "User", :foreign_key => :owner_id
  
  paginates_per 10
  
  attr_accessible :source, :source_file_name, :source_content_type, 
                  :source_file_size, :source_updated_at,
                  :description, :owner_id
                  
  has_attached_file :source,
                    :url => "/:current_user/:basename.:extension",
                    :path => "#{Rails.root}/public:url" 
                    
  validates_uniqueness_of :source_file_name
  
  private
                                        
  # interpolate in paperclip
  Paperclip.interpolates :current_user  do |attachment, style|
    attachment.instance.owner_id
  end
 
end
